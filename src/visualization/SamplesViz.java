package visualization;


//    protected int mBinNo = -1;
//    private Probe parentProbe;
//    private int timePeriod;
//
//    public SamplesViz(SessionAttributeManager sam, String probeID, int timePeriod) {
//        super(sam);
//        this.parentProbe = new Probe(probeID);
//        this.timePeriod = timePeriod;
//    }
//
//    @Override
//    protected int getNumberOfTimePeriods() {
//        return parentProbe.getExpression().getSamplesInTimePeriod(timePeriod);
//    }
//
//    public BufferedImage generateGraph() throws IOException {
//        initialize();
//        mVerticalBinSpacing = (mHeight - (2 * borderSize)) / mCountExpressionBins;
//
//        // Create image
//        BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2d = getVizCanvas(image);
//
//        imgMap.write("<Map name=\"Visualization_map\">\n");
//
//        renderProbe(g2d);
//
//        imgMap.write("</Map>");
//        imgMap.close();
//
//        return image;
//    }
//
//    protected Graphics2D renderProbe(Graphics2D g2d) {
//        timePeriodWidth = mWidth - (2 * borderSize) / mTimePeriodCount;
//
//        g2d.setColor(getColor(parentProbe));
//        Expression expression = parentProbe.getExpression();
//        double[] timePeriodExpression = expression.getConstituentExpressions(timePeriod);
//        for (int i = 0; i < timePeriodExpression.length; i++) {
//            double expressionValue = timePeriodExpression[i];
//
//            int xCoord = getXCoordForExpression(i);
//
//            int yCoord = (int) Math.round(mHeight - borderSize - (((expressionValue / trajectoryDocument.getBinUnit() )  - mMinExpressionBinValue) * mVerticalBinSpacing));
//
//            g2d.drawRect(xCoord - (boxSize / 2), yCoord - (boxSize / 2), boxSize, boxSize);
//            String area = "<area shape=\"rect\" coords=\"" +
//                    String.valueOf((int) xCoord) +
//                    "," + String.valueOf(yCoord) +
//                    "," + String.valueOf(xCoord + boxSize) +
//                    "," + String.valueOf(yCoord + boxSize) +
//                    " \" onclick=\"return exactNodeClick(event, " + i + ", " + parentProbe.getID() + ")\"/>\n";
//            imgMap.write(area);
//        }
//        
//        return g2d;
//    }
//
//    public Color getColor(Probe probe) {
//        Color col = Color.BLACK;
//        return col;
//    }
//
//    @Override
//    public void setImageHeight() {
//        this.mHeight = 200;
//    }
//
//    @Override
//    public void setImageWidth() {
//        this.mWidth = 350;
//    }
//    
//    @Override
//    protected int getMinimumExpressionBin() {
//        return (int) Math.round(parentProbe.getExpression().getMinimumExpressionInTimePeriod(timePeriod) / trajectoryDocument.getBinUnit());
//    }
//    
//    @Override
//    protected int getMaximumExpressionBin() {
//        return (int) Math.round(parentProbe.getExpression().getMaximumExpressionInTimePeriod(timePeriod) / trajectoryDocument.getBinUnit());
//    }
//    
//    @Override
//    protected void renderVerticalLines(Graphics2D g2d, Color color) {
//        Color lightBlue = new Color(191, 207, 255);
//        for (int i = 1; i <= getNumberOfTimePeriods() - 1; i++) {
//            g2d.setColor(lightBlue);
//            g2d.drawLine(
//                    (timePeriodWidth * i) + borderSize,
//                    borderSize,
//                    (timePeriodWidth * i) + borderSize,
//                    mHeight - borderSize);
//        }
//    }
//
//    @Override
//    protected Graphics2D renderXAxis(Graphics2D g2d, int verticalOffset, int verticalTextOffset) {
//        g2d.setColor(Color.BLACK);
//        g2d.drawLine(borderSize, verticalOffset, mWidth - borderSize, verticalOffset);
//
//        for (int i = 1; i <= getNumberOfTimePeriods(); i++) {
//            String sampleDescription = String.valueOf(i); //dda.getSampleDescription(i);
//            g2d.drawString(
//                    sampleDescription,
//                    (timePeriodWidth * (i - 1)) + borderSize + (timePeriodWidth / 3),
//                    verticalOffset + verticalTextOffset);
//        }
//        return g2d;
//    }
    
//}
