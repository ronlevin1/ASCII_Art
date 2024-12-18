package image;

//TODO: document
import java.awt.*;
import java.io.IOException;

public class ImagePadder {
    private final Image image;
    // half of the padding on each side.
    private final int paddingX;
    private final int paddingY;

    public ImagePadder(Image image) {
        this.image = image;
        /*
        calc padding s.t. img width and height are both divisible by 2.
        meaning, find the smallest padding s.t. the new width and height are
         both 2^k for some k.
         padding is simetric on each side.
         */
        this.paddingX = calcHalfPadding(image.getWidth());
        this.paddingY = calcHalfPadding(image.getHeight());
    }

    public Image pad() {
        int newWidth = image.getWidth() + 2 * paddingX;
        int newHeight = image.getHeight() + 2 * paddingY;
        Color[][] newPixelArray = new Color[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                // if we are in the padding area, set the pixel to white.
                if (i < paddingY || i >= newHeight - paddingY || j < paddingX || j >= newWidth - paddingX) {
                    newPixelArray[i][j] = new Color(255, 255, 255);
                } else {
                    // else, set the pixel to the original image's pixel.
                    newPixelArray[i][j] = image.getPixel(i - paddingY,
                            j - paddingX);
                }
            }
        }
        return new Image(newPixelArray, newWidth, newHeight);
    }

    /**
     * Calculates the padding needed to make the given dimension a power of 2.
     *
     * @param dim the dimension to pad. Must be positive and even.
     * @return the padding needed, ON EACH SIDE.
     */
    private int calcHalfPadding(int dim) {
        // if the dimension is not even, throw an exception.
        if (dim % 2 != 0) {
            throw new IllegalArgumentException("Dimension must be even.");
        }
        int k = 0;
        while (Math.pow(2, k) < dim) {
            k++;
        }
        int nextPowerOf2 = (int) Math.pow(2, k);
        return (nextPowerOf2 - dim) / 2;
    }

    // Main used for Testing.
//    public static void main(String[] args) throws IOException {
//        Image image = new Image("examples/board.jpeg");
//        ImagePadder padder = new ImagePadder(image);
//        Image paddedImage = padder.pad();
//        paddedImage.saveImage("padded_board");
//    }
}