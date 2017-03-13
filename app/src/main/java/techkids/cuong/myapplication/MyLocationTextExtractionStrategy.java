package techkids.cuong.myapplication;

import android.graphics.Rect;
import android.util.Log;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.parser.FilteredRenderListener;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.Matrix;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nghia on 3/12/2017.
 */

public class MyLocationTextExtractionStrategy implements TextExtractionStrategy {
    /** set to true for debugging */
    static boolean DUMP_STATE = false;

    /** a summary of all found text */
    private final List<MyLocationTextExtractionStrategy.TextChunk> locationalResult = new ArrayList<MyLocationTextExtractionStrategy.TextChunk>();

    private final MyLocationTextExtractionStrategy.TextChunkLocationStrategy tclStrat;


    /**
     * Creates a new text extraction renderer.
     */
    public MyLocationTextExtractionStrategy() {
        this(new MyLocationTextExtractionStrategy.TextChunkLocationStrategy() {
            public MyLocationTextExtractionStrategy.TextChunkLocation createLocation(TextRenderInfo renderInfo, LineSegment baseline) {
                return new MyLocationTextExtractionStrategy.TextChunkLocationDefaultImp(baseline.getStartPoint(), baseline.getEndPoint(), renderInfo.getSingleSpaceWidth());
            }
        });
    }

    /**
     * Creates a new text extraction renderer, with a custom strategy for
     * creating new TextChunkLocation objects based on the input of the
     * TextRenderInfo.
     * @param strat the custom strategy
     */
    public MyLocationTextExtractionStrategy(MyLocationTextExtractionStrategy.TextChunkLocationStrategy strat) {
        tclStrat = strat;
    }

    /**
     * @see com.itextpdf.text.pdf.parser.RenderListener#beginTextBlock()
     */
    public void beginTextBlock(){
    }

    /**
     * @see com.itextpdf.text.pdf.parser.RenderListener#endTextBlock()
     */
    public void endTextBlock(){
    }

    /**
     * @param str
     * @return true if the string starts with a space character, false if the string is empty or starts with a non-space character
     */
    private boolean startsWithSpace(String str){
        if (str.length() == 0) return false;
        return str.charAt(0) == ' ';
    }

    /**
     * @param str
     * @return true if the string ends with a space character, false if the string is empty or ends with a non-space character
     */
    private boolean endsWithSpace(String str){
        if (str.length() == 0) return false;
        return str.charAt(str.length()-1) == ' ';
    }

    /**
     * Filters the provided list with the provided filter
     * @param textChunks a list of all TextChunks that this strategy found during processing
     * @param filter the filter to apply.  If null, filtering will be skipped.
     * @return the filtered list
     * @since 5.3.3
     */
    private List<MyLocationTextExtractionStrategy.TextChunk> filterTextChunks(List<MyLocationTextExtractionStrategy.TextChunk> textChunks, MyLocationTextExtractionStrategy.TextChunkFilter filter){
        if (filter == null)
            return textChunks;

        List<MyLocationTextExtractionStrategy.TextChunk> filtered = new ArrayList<MyLocationTextExtractionStrategy.TextChunk>();
        for (MyLocationTextExtractionStrategy.TextChunk textChunk : textChunks) {
            if (filter.accept(textChunk))
                filtered.add(textChunk);
        }
        return filtered;
    }

    /**
     * Determines if a space character should be inserted between a previous chunk and the current chunk.
     * This method is exposed as a callback so subclasses can fine time the algorithm for determining whether a space should be inserted or not.
     * By default, this method will insert a space if the there is a gap of more than half the font space character width between the end of the
     * previous chunk and the beginning of the current chunk.  It will also indicate that a space is needed if the starting point of the new chunk
     * appears *before* the end of the previous chunk (i.e. overlapping text).
     * @param chunk the new chunk being evaluated
     * @param previousChunk the chunk that appeared immediately before the current chunk
     * @return true if the two chunks represent different words (i.e. should have a space between them).  False otherwise.
     */
    protected boolean isChunkAtWordBoundary(MyLocationTextExtractionStrategy.TextChunk chunk, MyLocationTextExtractionStrategy.TextChunk previousChunk){
        return chunk.getLocation().isAtWordBoundary(previousChunk.getLocation());
    }

    /**
     * Gets text that meets the specified filter
     * If multiple text extractions will be performed for the same page (i.e. for different physical regions of the page),
     * filtering at this level is more efficient than filtering using {@link FilteredRenderListener} - but not nearly as powerful
     * because most of the RenderInfo state is not captured in {@link MyLocationTextExtractionStrategy.TextChunk}
     * @param chunkFilter the filter to to apply
     * @return the text results so far, filtered using the specified filter
     */
    public String getResultantText(MyLocationTextExtractionStrategy.TextChunkFilter chunkFilter){
        if (DUMP_STATE) dumpState();

        List<MyLocationTextExtractionStrategy.TextChunk> filteredTextChunks = filterTextChunks(locationalResult, chunkFilter);
        Collections.sort(filteredTextChunks);

        StringBuilder sb = new StringBuilder();
        MyLocationTextExtractionStrategy.TextChunk lastChunk = null;
        for (MyLocationTextExtractionStrategy.TextChunk chunk : filteredTextChunks) {
            if (lastChunk == null){
                sb.append(chunk.text);
            } else {
                if (chunk.sameLine(lastChunk)){
                    // we only insert a blank space if the trailing character of the previous string wasn't a space, and the leading character of the current string isn't a space
                    if (isChunkAtWordBoundary(chunk, lastChunk) && !startsWithSpace(chunk.text) && !endsWithSpace(lastChunk.text)) {
                        //this is a word
                        sb.append(' ');
//                        chunk.getTextRenderInfo().get
                    }


                    sb.append(chunk.text);
                } else {
                    sb.append('\n');
                    sb.append(chunk.text);
                }
            }
            lastChunk = chunk;
        }

        return sb.toString();
    }

    public List<Rectangle> search(String query){
        if (DUMP_STATE) dumpState();

        List<Rectangle> coordinates = new ArrayList<>();
        List<MyLocationTextExtractionStrategy.TextChunk> filteredTextChunks = filterTextChunks(locationalResult, null);
        Collections.sort(filteredTextChunks);

        StringBuilder word = new StringBuilder();
        MyLocationTextExtractionStrategy.TextChunk lastChunk = null;
        for (MyLocationTextExtractionStrategy.TextChunk chunk : filteredTextChunks) {
            if (lastChunk == null){
                word.append(chunk.text);
            } else {
                if (chunk.sameLine(lastChunk)){
                    // we only insert a blank space if the trailing character of the previous string wasn't a space, and the leading character of the current string isn't a space
                    if (isChunkAtWordBoundary(chunk, lastChunk) && !startsWithSpace(chunk.text) && !endsWithSpace(lastChunk.text)) {
                        //this is a word
                        String wordStr = word.toString();
                        if (wordStr.toLowerCase().contains(query.toLowerCase())) {
                            //add this word to queue
                            coordinates.add(getBoundary(chunk));
                        }
//                        word.append(' ');
                        //reset word
                        word = new StringBuilder();
                    }
                    word.append(chunk.text);
                } else {

                    //another new word
                    String wordStr = word.toString();
                    if (wordStr.toLowerCase().contains(query.toLowerCase())) {
                        //add this word to queue
                        coordinates.add(getBoundary(chunk));
                    }
//                    word.append('\n');
                    word = new StringBuilder();
                    word.append(chunk.text);
                }
            }
            lastChunk = chunk;
        }
        return coordinates;
    }

    private Rectangle getBoundary(TextChunk textChunk) {
        Vector curBaseline = textChunk.getTextRenderInfo().getBaseline().getStartPoint();
        Vector topRight = textChunk.getTextRenderInfo().getAscentLine().getEndPoint();
        Rectangle rect = new Rectangle(curBaseline.get(0), curBaseline.get(1), topRight.get(0),topRight.get(1));
        return rect;
    }

    /**
     * Returns the result so far.
     * @return  a String with the resulting text.
     */
    public String getResultantText(){

        return getResultantText(null);

    }

    /** Used for debugging only */
    private void dumpState(){
        for (MyLocationTextExtractionStrategy.TextChunk location : locationalResult) {
            location.printDiagnostics();

            System.out.println();
        }

    }


    /**
     *
     * @see com.itextpdf.text.pdf.parser.RenderListener#renderText(com.itextpdf.text.pdf.parser.TextRenderInfo)
     */
    public void renderText(TextRenderInfo renderInfo) {
        LineSegment segment = renderInfo.getBaseline();
        if (renderInfo.getRise() != 0){ // remove the rise from the baseline - we do this because the text from a super/subscript render operations should probably be considered as part of the baseline of the text the super/sub is relative to
            Matrix riseOffsetTransform = new Matrix(0, -renderInfo.getRise());
            segment = segment.transformBy(riseOffsetTransform);
        }
        MyLocationTextExtractionStrategy.TextChunk tc = new MyLocationTextExtractionStrategy.TextChunk(renderInfo.getText(), tclStrat.createLocation(renderInfo, segment),renderInfo);
        locationalResult.add(tc);
    }

    public static interface TextChunkLocationStrategy {
        MyLocationTextExtractionStrategy.TextChunkLocation createLocation(TextRenderInfo renderInfo, LineSegment baseline);
    }

    public static interface TextChunkLocation extends Comparable<MyLocationTextExtractionStrategy.TextChunkLocation> {

        float distParallelEnd();

        float distParallelStart();

        int distPerpendicular();

        float getCharSpaceWidth();

        Vector getEndLocation();

        Vector getStartLocation();

        int orientationMagnitude();

        boolean sameLine(MyLocationTextExtractionStrategy.TextChunkLocation as);

        float distanceFromEndOf(MyLocationTextExtractionStrategy.TextChunkLocation other);

        boolean isAtWordBoundary(MyLocationTextExtractionStrategy.TextChunkLocation previous);
    }

    public static class TextChunkLocationDefaultImp implements MyLocationTextExtractionStrategy.TextChunkLocation {

        /** the starting location of the chunk */
        private final Vector startLocation;
        /** the ending location of the chunk */
        private final Vector endLocation;
        /** unit vector in the orientation of the chunk */
        private final Vector orientationVector;
        /** the orientation as a scalar for quick sorting */
        private final int orientationMagnitude;
        /** perpendicular distance to the orientation unit vector (i.e. the Y position in an unrotated coordinate system)
         * we round to the nearest integer to handle the fuzziness of comparing floats */
        private final int distPerpendicular;
        /** distance of the start of the chunk parallel to the orientation unit vector (i.e. the X position in an unrotated coordinate system) */
        private final float distParallelStart;
        /** distance of the end of the chunk parallel to the orientation unit vector (i.e. the X position in an unrotated coordinate system) */
        private final float distParallelEnd;
        /** the width of a single space character in the font of the chunk */
        private final float charSpaceWidth;

        public TextChunkLocationDefaultImp(Vector startLocation, Vector endLocation, float charSpaceWidth) {
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.charSpaceWidth = charSpaceWidth;

            Vector oVector = endLocation.subtract(startLocation);
            if (oVector.length() == 0) {
                oVector = new Vector(1, 0, 0);
            }
            orientationVector = oVector.normalize();
            orientationMagnitude = (int)(Math.atan2(orientationVector.get(Vector.I2), orientationVector.get(Vector.I1))*1000);

            // see http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html
            // the two vectors we are crossing are in the same plane, so the result will be purely
            // in the z-axis (out of plane) direction, so we just take the I3 component of the result
            Vector origin = new Vector(0,0,1);
            distPerpendicular = (int)(startLocation.subtract(origin)).cross(orientationVector).get(Vector.I3);

            distParallelStart = orientationVector.dot(startLocation);
            distParallelEnd = orientationVector.dot(endLocation);
        }


        public int orientationMagnitude() {return orientationMagnitude;}
        public int distPerpendicular() {return distPerpendicular;}
        public float distParallelStart() {return distParallelStart; }
        public float distParallelEnd() { return distParallelEnd;}


        /**
         * @return the start location of the text
         */
        public Vector getStartLocation(){
            return startLocation;
        }

        /**
         * @return the end location of the text
         */
        public Vector getEndLocation(){
            return endLocation;
        }

        /**
         * @return the width of a single space character as rendered by this chunk
         */
        public float getCharSpaceWidth() {
            return charSpaceWidth;
        }


        /**
         * @param as the location to compare to
         * @return true is this location is on the the same line as the other
         */
        public boolean sameLine(MyLocationTextExtractionStrategy.TextChunkLocation as){
            return orientationMagnitude() == as.orientationMagnitude() && distPerpendicular() == as.distPerpendicular();
        }

        /**
         * Computes the distance between the end of 'other' and the beginning of this chunk
         * in the direction of this chunk's orientation vector.  Note that it's a bad idea
         * to call this for chunks that aren't on the same line and orientation, but we don't
         * explicitly check for that condition for performance reasons.
         * @param other
         * @return the number of spaces between the end of 'other' and the beginning of this chunk
         */
        public float distanceFromEndOf(MyLocationTextExtractionStrategy.TextChunkLocation other){
            float distance = distParallelStart() - other.distParallelEnd();
            return distance;
        }

        public boolean isAtWordBoundary(MyLocationTextExtractionStrategy.TextChunkLocation previous){
            /**
             * Here we handle a very specific case which in PDF may look like:
             * -.232 Tc [( P)-226.2(r)-231.8(e)-230.8(f)-238(a)-238.9(c)-228.9(e)]TJ
             * The font's charSpace width is 0.232 and it's compensated with charSpacing of 0.232.
             * And a resultant TextChunk.charSpaceWidth comes to TextChunk constructor as 0.
             * In this case every chunk is considered as a word boundary and space is added.
             * We should consider charSpaceWidth equal (or close) to zero as a no-space.
             */
            if (getCharSpaceWidth() < 0.1f)
                return false;

            float dist = distanceFromEndOf(previous);

            return dist < -getCharSpaceWidth() || dist > getCharSpaceWidth()/2.0f;
        }

        public int compareTo(MyLocationTextExtractionStrategy.TextChunkLocation other) {
            if (this == other) return 0; // not really needed, but just in case

            int rslt;
            rslt = compareInts(orientationMagnitude(), other.orientationMagnitude());
            if (rslt != 0) return rslt;

            rslt = compareInts(distPerpendicular(), other.distPerpendicular());
            if (rslt != 0) return rslt;

            return Float.compare(distParallelStart(), other.distParallelStart());
        }
    }
    /**
     * Represents a chunk of text, it's orientation, and location relative to the orientation vector
     */
    public static class TextChunk implements Comparable<MyLocationTextExtractionStrategy.TextChunk>{
        /** the text of the chunk */
        private final String text;
        private final MyLocationTextExtractionStrategy.TextChunkLocation location;
        private final TextRenderInfo textRenderInfo;

        public TextChunk(String string, Vector startLocation, Vector endLocation, float charSpaceWidth, TextRenderInfo textRenderInfo) {
            this(string, new MyLocationTextExtractionStrategy.TextChunkLocationDefaultImp(startLocation, endLocation, charSpaceWidth),textRenderInfo);
        }

        public TextChunk(String string, MyLocationTextExtractionStrategy.TextChunkLocation loc, TextRenderInfo textRenderInfo) {
            this.text = string;
            this.location = loc;
            this.textRenderInfo = textRenderInfo;
        }

        public TextRenderInfo getTextRenderInfo() {
            return textRenderInfo;
        }

        /**
         * @return the text captured by this chunk
         */
        public String getText(){
            return text;
        }

        /**
         * @return an object holding location data about this TextChunk
         */
        public MyLocationTextExtractionStrategy.TextChunkLocation getLocation() {
            return location;
        }

        /**
         * @return the start location of the text
         */
        public Vector getStartLocation(){
            return location.getStartLocation();
        }
        /**
         * @return the end location of the text
         */
        public Vector getEndLocation(){
            return location.getEndLocation();
        }

        /**
         * @return the width of a single space character as rendered by this chunk
         */
        public float getCharSpaceWidth() {
            return location.getCharSpaceWidth();
        }

        /**
         * Computes the distance between the end of 'other' and the beginning of this chunk
         * in the direction of this chunk's orientation vector.  Note that it's a bad idea
         * to call this for chunks that aren't on the same line and orientation, but we don't
         * explicitly check for that condition for performance reasons.
         * @param other the other {@link MyLocationTextExtractionStrategy.TextChunk}
         * @return the number of spaces between the end of 'other' and the beginning of this chunk
         */
        public float distanceFromEndOf(MyLocationTextExtractionStrategy.TextChunk other){
            return location.distanceFromEndOf(other.location);
        }

        private void printDiagnostics(){
            System.out.println("Text (@" + location.getStartLocation() + " -> " + location.getEndLocation() + "): " + text);
            System.out.println("orientationMagnitude: " + location.orientationMagnitude());
            System.out.println("distPerpendicular: " + location.distPerpendicular());
            System.out.println("distParallel: " + location.distParallelStart());
        }

        /**
         * Compares based on orientation, perpendicular distance, then parallel distance
         * @param rhs the other object
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(MyLocationTextExtractionStrategy.TextChunk rhs) {
            return location.compareTo(rhs.location);
        }

        private boolean sameLine(MyLocationTextExtractionStrategy.TextChunk lastChunk) {
            return getLocation().sameLine(lastChunk.getLocation());
        }
    }


    /**
     *
     * @param int1
     * @param int2
     * @return comparison of the two integers
     */
    private static int compareInts(int int1, int int2){
        return int1 == int2 ? 0 : int1 < int2 ? -1 : 1;
    }

    /**
     * no-op method - this renderer isn't interested in image events
     * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(com.itextpdf.text.pdf.parser.ImageRenderInfo)
     * @since 5.0.1
     */
    public void renderImage(ImageRenderInfo renderInfo) {
        // do nothing
    }

    /**
     * Specifies a filter for filtering {@link MyLocationTextExtractionStrategy.TextChunk} objects during text extraction
     * @see MyLocationTextExtractionStrategy#getResultantText(MyLocationTextExtractionStrategy.TextChunkFilter)
     * @since 5.3.3
     */
    public static interface TextChunkFilter{
        /**
         * @param textChunk the chunk to check
         * @return true if the chunk should be allowed
         */
        public boolean accept(MyLocationTextExtractionStrategy.TextChunk textChunk);
    }
}
