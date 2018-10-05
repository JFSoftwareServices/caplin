package caplin.com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Text blob generator takes an instruction string
 * <p>
 * Generates a blob of text using the characters a to f, (should loop back to a if number of letters to be generated is more than 6)
 * <p>
 * Then pads the blob with dashes to either the left or right depending on the padding style,
 * <p>
 * or no padding if no padding style defined
 * <p>
 * eg. if input instruction string:
 * addLettersFor:3-loops,padLeftFor:5-loops,padRightFor:6-loops,paddingStyle:left
 * output should be:
 * -----abc
 * <p>
 * eg. if input instruction string:
 * addLettersFor:8-loops,padLeftFor:5-loops,padRightFor:7-loops,paddingStyle:right
 * output should be:
 * abcdefab-------
 * <p>
 * eg. if input instruction string:
 * addLettersFor:8-loops,padLeftFor:5-loops,padRightFor:7-loops
 * output should be:
 * abcdefab
 * <p>
 * 1) fix the compile issues and run the main method
 * <p>
 * 2) correct any mistakes in logic
 * <p>
 * 3) Make the code cleaner (refactor and simplify classes)
 * <p>
 * 4) test that it works as intended
 */
public class TextBlobGenerator {

    private static final String[] LETTER_BLOCK = {"a", "b", "c", "d", "e", "f"};
    private static final int LETTER_BLOCK_SIZE = 6;
    private int addLettersFor;
    private int padLeftFor;
    private int padRightFor;
    private String paddingStyle = "";
    private String blob = "";

    private TextBlobGenerator(DataWrapper dataWrapper) {
        String instruction = dataWrapper.getInstruction();
        parseInstruction(instruction);
        generateLettersForBlob();
        padBlob();
    }

    private void parseInstruction(String instructions) {
        Pattern pattern = Pattern.compile(".*:(\\d+)-.*:(\\d+)-.*:(\\d+)-\\w+(?:,paddingStyle:(left|right))?");
        Matcher m = pattern.matcher(instructions);
        if (m.find()) {
            addLettersFor = Integer.parseInt(m.group(1));
            padLeftFor = Integer.parseInt(m.group(2));
            padRightFor = Integer.parseInt(m.group(3));
            paddingStyle = (m.group(4) == null) ? "" : m.group(4);
        }
    }

    private void generateLettersForBlob() {
        if (addLettersFor <= LETTER_BLOCK_SIZE) {
            generateBlobLettersWithNoLoopBack();
        } else {
            generateBlobLettersWithLoopBack();
        }
    }

    private void generateBlobLettersWithNoLoopBack() {
        for (int i = 0; i < addLettersFor; i++) {
            blob = blob + LETTER_BLOCK[i];
        }
    }

    private void generateBlobLettersWithLoopBack() {
        int numberOfLetterBlocks = addLettersFor / LETTER_BLOCK_SIZE;
        String joinedLetterBlock = generateJoinedLetterBlock();
        for (int i = 0; i < numberOfLetterBlocks; i++) {
            blob = blob + joinedLetterBlock;
        }

        int numberOfRemainingLetters = addLettersFor % LETTER_BLOCK_SIZE;
        for (int i = 0; i < numberOfRemainingLetters; i++) {
            blob = blob + LETTER_BLOCK[i];
        }
    }

    private String generateJoinedLetterBlock() {
        String block = "";
        for (int i = 0; i < LETTER_BLOCK_SIZE; i++) {
            block = block + LETTER_BLOCK[i];
        }
        return block;
    }

    private void padBlob() {
        if ("left".equals(paddingStyle)) {
            padBlobLeft();
        } else if ("right".equals(paddingStyle)) {
            padBlobRight();
        } else {
            padBlobNone();
        }
    }

    private void padBlobNone() {
        blob = blob + "";
    }

    private void padBlobRight() {
        for (int i = 0; i < padRightFor; i++) {
            blob = blob + "-";
        }
    }

    private void padBlobLeft() {
        for (int i = 0; i < padLeftFor; i++) {
            blob = "-" + blob;
        }
    }

    private String getBlob() {
        return blob;
    }

    private static class DataWrapper {
        String instruction;

        String getInstruction() {
            return instruction;
        }

        void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DataWrapper that = (DataWrapper) o;

            return instruction.equals(that.instruction);
        }

        @Override
        public int hashCode() {
            return instruction.hashCode();
        }
    }

    public static void main(String[] args) {
        DataWrapper dataWrapper = new DataWrapper();
        dataWrapper.setInstruction("addLettersFor:9-loops,padLeftFor:99-loops,padRightFor:999-loops,paddingStyle:left");
//        dataWrapper.instruction = "addLettersFor:8-loops,padLeftFor:5-loops,padRightFor:7-loops,paddingStyle:right";
//        dataWrapper.instruction = "addLettersFor:12-loops,padLeftFor:99-loops,padRightFor:999-loops,paddingStyle:left";
//        dataWrapper.instruction = "addLettersFor:13-loops,padLeftFor:99-loops,padRightFor:999-loops,paddingStyle:left";
//        dataWrapper.instruction = "addLettersFor:0-loops,padLeftFor:99-loops,padRightFor:0-loops,paddingStyle:right";
//        dataWrapper.instruction = "addLettersFor:0-loops,padLeftFor:99-loops,padRightFor:1-loops,paddingStyle:right";
//        dataWrapper.instruction = "addLettersFor:1-loops,padLeftFor:99-loops,padRightFor:1-loops,paddingStyle:right";
//        dataWrapper.instruction = "addLettersFor:2-loops,padLeftFor:99-loops,padRightFor:2-loops,paddingStyle:right";
//        dataWrapper.instruction = "addLettersFor:0-loops,padLeftFor:0-loops,padRightFor:999-loops,paddingStyle:left";
//        dataWrapper.instruction = "addLettersFor:0-loops,padLeftFor:1-loops,padRightFor:999-loops,paddingStyle:left";
//        dataWrapper.instruction = "addLettersFor:1-loops,padLeftFor:1-loops,padRightFor:999-loops,paddingStyle:left";
//        dataWrapper.instruction = "addLettersFor:2-loops,padLeftFor:2-loops,padRightFor:999-loops,paddingStyle:left";
//        dataWrapper.instruction = "addLettersFor:0-loops,padLeftFor:1-loops,padRightFor:0-loops";
//        dataWrapper.instruction = "addLettersFor:0-loops,padLeftFor:0-loops,padRightFor:1-loops";
//        dataWrapper.instruction = "addLettersFor:1-loops,padLeftFor:1-loops,padRightFor:0-loops";
//        dataWrapper.instruction = "addLettersFor:1-loops,padLeftFor:0-loops,padRightFor:1-loops";
        System.out.println(new TextBlobGenerator(dataWrapper).getBlob());
    }
}