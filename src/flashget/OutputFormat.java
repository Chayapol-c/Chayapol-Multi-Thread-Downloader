package flashget;

/**
 * Make a String in nice formatting.
 *
 * @author Chayapol 6210545947
 */
public class OutputFormat {

    /**
     * Convert file length into Scientific notation form.
     *
     * @param value is file length.
     * @return String that converted
     */
    public String convertByte(double value) {
        if (value >= 1.0E9) return String.valueOf(value / 1.0E9) + " GB";
        else if (value >= 1.0E6) return String.valueOf(value / 1.0E6) + " MB";
        else if (value >= 1.0E3) return String.valueOf(value / 1.0E3) + " KB";
        return String.valueOf(value);
    }

    /**
     * convert remaining time in to each unit.
     *
     * @param remainingTime is remaining download time in double
     * @return String of remaining time.
     */
    public String updateRemainingTime(double remainingTime) {
        String unit = "";
        if (remainingTime >= 3600) {
            unit = "hours";
            remainingTime = remainingTime / 3600;

        } else if (remainingTime >= 60) {
            unit = "minutes";
            remainingTime = remainingTime / 60;
        } else {
            unit = "seconds";
            remainingTime = remainingTime;
        }
        return String.format("Remaining Time : %.0f %s", remainingTime, unit);
    }

    public String byteConvert(double downloaded, double fileLength) {
        return String.format("%s / %s", convertByte(downloaded), convertByte(fileLength));
    }
}
