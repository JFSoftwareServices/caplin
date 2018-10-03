package caplin.com;

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
        String[] instructionTokens = instructions.split(":");
        addLettersFor = Integer.parseInt(instructionTokens[1].split("-")[0]);
        padLeftFor = Integer.parseInt(instructionTokens[2].split("-")[0]);
        padRightFor = Integer.parseInt(instructionTokens[3].split("-")[0]);
        paddingStyle = (isPaddingStyleSpecified(instructionTokens)) ? instructionTokens[4] : "";
    }

    private boolean isPaddingStyleSpecified(String[] InstructionTokens) {
        return InstructionTokens.length == 5;
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

    private void generateLettersForBlob() {
        if (addLettersFor <= LETTER_BLOCK_SIZE) {
            generateLettersWithNoLoopBack();
        } else {
            generateLettersWithLoopBack();
        }
    }

    private void generateLettersWithNoLoopBack() {
        for (int i = addLettersFor - 1; i >= 0; i--) {
            blob = LETTER_BLOCK[i] + blob;
        }
    }

    private void generateLettersWithLoopBack() {
        generateLetterBlocks();

        int numberOfLoopBackLetters = addLettersFor % LETTER_BLOCK_SIZE;
        if (numberOfLoopBackLetters > 0) {
            generatePartialLetterBlock(0, numberOfLoopBackLetters - 1);
        }
    }

    private void generateLetterBlocks() {
        int numberOfLetterBlocks = addLettersFor / LETTER_BLOCK_SIZE;
        while (numberOfLetterBlocks > 0) {
            repeatLetterBlock(0, LETTER_BLOCK_SIZE - 1);
            numberOfLetterBlocks--;
        }
    }

    private void repeatLetterBlock(int beginIndex, int endIndex) {
        while (endIndex >= beginIndex) {
            blob = LETTER_BLOCK[endIndex] + blob;
            endIndex--;
        }
    }

    private void generatePartialLetterBlock(int beginIndex, int endIndex) {
        while (beginIndex <= endIndex) {
            blob = blob + LETTER_BLOCK[beginIndex];
            beginIndex++;
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
        dataWrapper.instruction = "addLettersFor:9-loops,padLeftFor:99-loops,padRightFor:999-loops,paddingStyle:left";
        dataWrapper.instruction = "addLettersFor:8-loops,padLeftFor:5-loops,padRightFor:7-loops,paddingStyle:right";
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