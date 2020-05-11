package flashget;

        public class OutputFormat {

            public static String convertByte(double value) {
                if (value >= 1.0E9) return String.valueOf(value / 1.0E9) + " GB";
                else if (value >= 1.0E6) return String.valueOf(value / 1.0E6) + " MB";
                else if (value >= 1.0E3) return String.valueOf(value / 1.0E3) + " KB";
                return String.valueOf(value);
            }

            public static String updateRemainingTime(double remainingTime){
                String text = "";
                if(remainingTime >= 3600) {
                        text = String.format("Remaining Time : %.0f hours" , (remainingTime/3600));
                    }
                else if(remainingTime >= 60){
                        text = String.format("Remaining Time : %.0f minutes", remainingTime/60);
                    }
                else{
                        text = String.format("Remaining Time : %.0f seconds", remainingTime);
                    }
                return text;
            }
}
