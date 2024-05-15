package za.co.recruitmentzone.vacancy.entity;


import io.opencensus.metrics.export.TimeSeries;
import za.co.recruitmentzone.application.entity.Application;


import java.sql.Timestamp;
import java.util.*;

public class ApplicationComparator implements Comparator<Application> {
    @Override
    public int compare(Application v1, Application v2) {

        Timestamp timestamp1 = Timestamp.valueOf(v1.getDate_received());


        Timestamp timestamp2 = Timestamp.valueOf(v2.getDate_received());
        // This will sort in descending order (newest first)
         return timestamp1.compareTo(timestamp2);

        //  return timestamp1.compareTo(timestamp2);
    }


    public Set<Application> sortApplicationsByDateReceived(Set<Application> applications) {
        // Create a TreeSet with a custom Comparator to sort by dateReceived
        TreeSet<Application> sortedApplications = new TreeSet<>(new Comparator<Application>() {
            @Override
            public int compare(Application app1, Application app2) {
                return app1.getDate_received().compareTo(app2.getDate_received());
            }
        });

        // Add all applications to the sorted TreeSet
        sortedApplications.addAll(applications);

        // Return the sorted set
        return sortedApplications;
    }


}