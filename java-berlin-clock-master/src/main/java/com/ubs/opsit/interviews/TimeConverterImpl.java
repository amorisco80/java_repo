package com.ubs.opsit.interviews;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by ANTONIO on 03/03/2015.
 */
public class TimeConverterImpl implements TimeConverter {

    @Override
    public String convertTime(String aTime) {

        final String SEPARATOR = "\r\n";

        Date time = null;
        // set default values for the time
        int iHour =  0;
        int iMinutes =  0;
        int iSeconds = 0;

        try{
            if(aTime != null) {
                time = new SimpleDateFormat("HH:mm:ss").parse(aTime);
                Calendar c = Calendar.getInstance();
                c.setTime(time);
                // Although 00:00 and 24:00 mean the same, the notation 24:00 is not used.
                // However, in order to pass this test I need to manage this exception
                String sHour = aTime.split(":")[0];
                if(sHour.equals("24")) {
                    iHour = Integer.parseInt(sHour);
                }else
                    iHour = c.get(Calendar.HOUR_OF_DAY);
                iMinutes = c.get(Calendar.MINUTE);
                iSeconds = c.get(Calendar.SECOND);
            }
        }catch(ParseException pEx){
            System.out.println("Error in parsing " + aTime + " date: " + pEx.getMessage());
        }

        // the yellow lamp on the first row that blinks on/off every two seconds
        String firstRow = (iSeconds % 2 == 0 ? "Y":"O");
        StringBuilder secondRow = new StringBuilder();
        StringBuilder thirdRow = new StringBuilder();
        StringBuilder fourthRow = new StringBuilder();
        StringBuilder fifthRow = new StringBuilder();

        // _5HourLamps variable tells me how many lamps of 5 hours I'll have
        int _5HourLamps = iHour / 5;
        // cycle on the 4 lamps of the second row activating lamps of 5 hours
        for (int i = 1 ; i<5; i++){
            secondRow.append(_5HourLamps >= i? "R":"O");
        }

        // _1HourLamps variable tells me how many lamps of 1 hour I'll have
        int _1HourLamps = iHour % 5;
        // cycle on the 4 lamps of the third row activating lamps of 1 hour
        for(int i = 1; i<5; i++){
            thirdRow.append(_1HourLamps >= i ? "R" : "O");
        }

        // _1MinLamp variable tells me how many lamps of 1 minute I'll have
        int _1MinLamps = iMinutes % 5;

        // cycle on the 11 lamps of the fourth row
        for (int i = 1 ; i<12; i++) {

            // concats yellow or red lamps for every 5 minutes given the position
            fourthRow.append(iMinutes >= i * 5 ? getLampColor(i) : "O");
        }
        // cycle on the 4 lamps of the fifth row, activating yellow lamps of 1 minute
        for(int i = 1; i<5; i++){
            fifthRow.append(_1MinLamps >= i? "Y":"O");
        }

        // Concats the rows of lamps
        // Note: The use of a mutable object as StringBuilder is preferable to an immutable object as String, when it needs to be changed
        return new StringBuilder(firstRow).append(SEPARATOR)
                .append(secondRow.toString()).append(SEPARATOR)
                .append(thirdRow.toString()).append(SEPARATOR)
                .append(fourthRow.toString()).append(SEPARATOR)
                .append(fifthRow.toString()).toString();
    }

    /**
     * Given the position of the lamp, the method returns the lamp's color
     * @param position
     * @return the color of Lamp
     */
    private static String getLampColor(int position){

        // In this first row the 3rd, 6th and 9th lamp are red and indicate the first quarter, half and last quarter of an hour.
        if( position == 3 || position == 6 || position == 9){
            return "R";
        }
        else //The other lamps are yellow.
            return "Y";
    }
}
