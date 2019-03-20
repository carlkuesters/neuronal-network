package neuronalnetwork.logic.training.data;

/**

 @author Carl
 */
class GrayscaleImage {

    private int pixelRows;
    private int pixelColumns;
    private int[] grayValues;

    GrayscaleImage(int pixelRows, int pixelColumns, int[] grayValues) {
        this.pixelRows = pixelRows;
        this.pixelColumns = pixelColumns;
        this.grayValues = grayValues;
    }

    int getPixelRows() {
        return pixelRows;
    }

    int getPixelColumns() {
        return pixelColumns;
    }

    int[] getGrayValues() {
        return grayValues;
    }
}
