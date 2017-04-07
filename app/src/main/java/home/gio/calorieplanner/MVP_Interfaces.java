package home.gio.calorieplanner;


import home.gio.calorieplanner.model.CalorieCalculator;

public class MVP_Interfaces {

    /*
        Required view methods available to presenter
        A passive layer, responsible to show data
        and receive user interactions
        Presenter to View
     */
    public interface RequiredViewOperations {

    }

    /*
    Required presenter methods available to model
    Model to Presenter
     */
    public interface RequiredPresenterOperations {

    }

    /*
       Operations offered to view to communicate with presenter
        Process user interaction, sends data requests to Model, etc.
        view to presenter
     */
    public interface ProvidedPresenterOperations {
        String sendInches(String inches);

        double getCentimeters(String feet, String inches);

        double convertToCentimeter(String feet, String inches);

        String convertTofeetInches(String str);

        int getFeet(String feetAndInches);

        int getInches(String feetAndInches);

        void onConfigurationChanged(RequiredViewOperations view);
    }

    /*
      Operations offered to model to communicate with presenter
      Handles all data business logic.
      Presenter to Model
     */
    public interface ProvidedModelOperations {
        String sendInches(String inches);

        double convertToCentimeter(String feet, String inches);

        String convertTofeetInches(String str);

        int getFeet(String feetAndInches);

        int getInches(String feetAndInches);
    }


}
