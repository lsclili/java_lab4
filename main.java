import java.util.ArrayList;
import java.util.List;

public class LabWork5 {
    public static void main(String[] args) {
        int studentNumber = 4;
        int C11 = studentNumber % 11;
        System.out.println("C11: " + C11);

        try {
            if (C11 == 4) {
                String inputText = "Це перше речення. А це друге речення! Чи це третє речення? Що буде з четвертим реченням?";
                inputText = inputText.replaceAll("[\\t ]+", " ");
                Text text = new Text(inputText);
                System.out.println("Заданий текст:");
                System.out.println(text.getOriginalText());
                text.processText();
                System.out.println("Оброблений текст:");
                System.out.println(text.getProcessedText());
            } else {
                throw new UnsupportedOperationException("Невідомий варіант завдання для C11 = " + C11);
            }
        } catch (Exception e) {
            System.err.println("Сталася помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class Letter {
    private char character;

    public Letter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }
}

class PunctuationMark {
    private char mark;

    public PunctuationMark(char mark) {
        this.mark = mark;
    }

    public char getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return String.valueOf(mark);
    }
}

class Word {
    private List<Letter> letters;

    public Word(String word) {
        letters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    public String getWord() {
        StringBuilder sb = new StringBuilder();
        for (Letter letter : letters) {
            sb.append(letter.getCharacter());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getWord();
    }
}

class Sentence {
    private List<Object> elements;

    public Sentence(String sentence) {
        elements = new ArrayList<>();
        String[] parts = sentence.split("(?<=[.!?])");
        String[] tokens = parts[0].trim().split("\\s+");
        for (String token : tokens) {
            if (token.matches("[.!?]")) {
                elements.add(new PunctuationMark(token.charAt(0)));
            } else {
                elements.add(new Word(token));
            }
        }
        if (parts.length > 1) {
            elements.add(new PunctuationMark(parts[1].charAt(0)));
        }
    }

    public void swapFirstLastWord() {
        int firstWordIndex = -1;
        int lastWordIndex = -1;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i) instanceof Word) {
                firstWordIndex = i;
                break;
            }
        }
        for (int i = elements.size() - 1; i >= 0; i--) {
            if (elements.get(i) instanceof Word) {
                lastWordIndex = i;
                break;
            }
        }
        if (firstWordIndex != -1 && lastWordIndex != -1 && firstWordIndex != lastWordIndex) {
            Word firstWord = (Word) elements.get(firstWordIndex);
            Word lastWord = (Word) elements.get(lastWordIndex);
            elements.set(firstWordIndex, lastWord);
            elements.set(lastWordIndex, firstWord);
        }
    }

    public String getSentence() {
        StringBuilder sb = new StringBuilder();
        for (Object obj : elements) {
            sb.append(obj.toString()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return getSentence();
    }
}

class Text {
    private List<Sentence> sentences;
    private String originalText;
    private String processedText;

    public Text(String text) {
        originalText = text;
        sentences = new ArrayList<>();
        String[] parts = text.split("(?<=[.!?])\\s*");
        for (String part : parts) {
            sentences.add(new Sentence(part));
        }
    }

    public void processText() {
        for (Sentence sentence : sentences) {
            sentence.swapFirstLastWord();
        }
        StringBuilder sb = new StringBuilder();
        for (Sentence sentence : sentences) {
            sb.append(sentence.toString()).append(" ");
        }
        processedText = sb.toString().trim();
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getProcessedText() {
        return processedText;
    }
}
