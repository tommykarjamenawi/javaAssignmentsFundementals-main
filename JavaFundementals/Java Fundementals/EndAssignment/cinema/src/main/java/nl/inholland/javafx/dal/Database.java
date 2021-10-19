package nl.inholland.javafx.dal;


import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateTimeStringConverter;
import nl.inholland.javafx.model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Database {
    private final List<Person> persons;
    private final ObservableList<Movie> movies;
    public final ObservableList<Room> room1;
    public final ObservableList<Room> room2;

    public Database() {
        persons = new ArrayList<>();
        movies = FXCollections.observableArrayList();
        room1 = FXCollections.observableArrayList();
        room2 = FXCollections.observableArrayList();
        initialize();
    }

    public void initialize() {
        readPersons();
        readMovies();
    }



    private void readPersons() {
        try (Scanner teacherScanner = new Scanner(new File("src/main/java/nl/inholland/javafx/files/persons.csv"))) {
            while (true) {
                try {
                    String line = teacherScanner.nextLine();
                    String[] personArray = line.split(",");
                    Person person = new Person(personArray[0], personArray[1], LocalDate.parse(personArray[2]), personArray[3], personArray[4], personArray[5]);
                    persons.add(person);
                } catch (NoSuchElementException nsee) {
                    break;
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public boolean savePersons(List<Person> persons) {
        try (Writer writer = new FileWriter("src/main/java/nl/inholland/javafx/files/persons.csv")) {
            for (Person person : persons) {
                String teacherString = String.format("%s,%s,%s,%s,%s,%s\n",
                        person.getFirstName(),
                        person.getLastName(),
                        person.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        person.getUserName(),
                        person.getPassword(),
                        person.getRole());
                writer.write(teacherString);
            }

            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    private void readMovies() {
        try (Scanner movieScanner = new Scanner(new File("src/main/java/nl/inholland/javafx/files/movies.csv"))) {
            while (true) {
                try {
                    String line = movieScanner.nextLine();
                    String[] movieArray = line.split(",");
                    movies.add(new Movie(movieArray[0], Integer.parseInt(movieArray[1]), Double.parseDouble(movieArray[2])));
                } catch (NoSuchElementException nsee) {
                    break;
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    /*public boolean saveMovies(List<Movie> persons) {
        try (Writer writer = new FileWriter("src/main/java/nl/inholland/javafx/files/movies.csv")) {
            for (Movie movie : persons) {
                String movieString = String.format("%s,%s,%s,%s,%s\n",
                        //movie.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss")),
                        //movie.getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss")),
                        movie.getTitle(),
                        movie.getSeats(),
                        movie.getPrice());
                writer.write(movieString);
            }

            return true;
        } catch (IOException ioe) {
            return false;
        }
    }*/
}
