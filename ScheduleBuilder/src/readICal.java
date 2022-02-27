import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.*;
import org.joda.time.Instant;

import biweekly.*;
import biweekly.component.VEvent;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.util.Period;

public class readICal {
    DateTime now = DateTime.now();
    org.joda.time.DateTime weekStart;
    org.joda.time.DateTime weekEnd;
    ICalendar ical;


    public readICal(String fileName) {
        try {
            ical = Biweekly.parse(new FileReader(fileName)).first();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public readICal(String[] fileNames) {
        try {
            List<ICalendar> icals = Biweekly.parse(new FileReader(fileNames[0])).all();
            for(int i = 1; i < fileNames.length; i++) {
                 icals.add(Biweekly.parse(new FileReader(fileNames[i])).first());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWeek(DateTime now) {
        int iDayNow = now.getDayOfWeek();
        weekStart = now.minusDays((iDayNow - 1));
        weekEnd = now.plusDays(7 - iDayNow);
        //System.out.println(weekEnd);
        //System.out.println(weekStart);
    }

    public ICalendar daysInterested() {
        ICalendar newICal = new ICalendar();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        for(VEvent event: this.ical.getEvents()) {
            System.out.println(event.getSummary().getValue());
            DateTime dtStart = new DateTime((Date) event.getDateStart().getValue());
            if(!dtStart.isBefore(this.weekStart) && !dtStart.isAfter(this.weekEnd)) {
                newICal.addEvent(event);
                System.out.println(event.getSummary().getValue());

            }
        
            //System.out.println((Date) event.getDateStart().getValue());
        }

        return newICal;

    }

    public Schedule setEventsToDays(ICalendar newICal) {
        Schedule schedule = new Schedule("Person 1");
        int count = 0;
        for(VEvent event : newICal.getEvents()) {
            DateStart eventStart = event.getDateStart();
            DateEnd eventEnd = event.getDateEnd();

            if(eventStart != null) {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                
                String start = df.format(eventStart);
                String end = df.format(eventEnd);
                System.out.println(start + "     " + end);
                int startHours = Integer.parseInt(start.substring(11,13));
                double startMins = Integer.parseInt(start.substring(14,16));
                int endHours = Integer.parseInt(end.substring(11,13));
                double endMins = Integer.parseInt(end.substring(14,16));
                double doubleStart = startHours + (startMins / 60);
                double doubleEnd = endHours + (endMins / 60);
                DateTime data = DateTime.parse(start);
                int iDayNow = data.getDayOfWeek();
                String dayString = "";
                if(iDayNow == 1) {
                    dayString = "Monday";
                } else if (iDayNow == 2) {
                    dayString = "Tuesday";
                } else if (iDayNow == 3) {
                    dayString = "Wednesday";
                } else if (iDayNow == 4) {
                    dayString = "Thursday";
                } else if (iDayNow == 5) {
                    dayString = "Friday";
                } else if (iDayNow == 6) {
                    dayString = "Saturday";
                } else if (iDayNow == 7) {
                    dayString = "Sunday";
                }
                
                schedule.addEvent(dayString, event.getSummary().getValue(), doubleStart, doubleEnd, true);
                
                //DateTime dt = new DateTime(df);
                //schedule.getDay(count).addEvent()
            }
            
        }

        return schedule;
    }

    // public static void main(String[] args) throws FileNotFoundException, IOException {

    //     // parse the first iCalendar object from the data stream
    //     //ICalendar ical = Biweekly.parse(new FileReader("ScheduleBuilder/src/Spring Term 2021-2022 (1).ics")).first();

    //     // or parse all objects from the data stream
    //     List<ICalendar> icals = Biweekly.parse(new FileReader("ScheduleBuilder/src/Spring Term 2021-2022 (1).ics")).all();

    //     VEvent event = icals.get(0).getEvents().get(1);
    //     String summary = event.getSummary().getValue();
    //     System.out.print(event);
    // }

}