import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @org.junit.jupiter.api.Test
    public void constructor_NullNameParamPassed_ThrowsIllegalArgumentException(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\n\n", "\t", "\t \t", "\t\t"})
    public void constructor_NullNameParamPassed_ThrowsIllegalArgumentException(String name){
        String expectedMessage = "Name cannot be blank.";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    public void constructor_NegativeSpeedParamPassed_ThrowsIllegalArgumentException(){
        String expectedMessage = "Speed cannot be negative.";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("test", -5, 2));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    public void constructor_NegativeDistanceParamPassed_ThrowsIllegalArgumentException(){
        String expectedMessage = "Distance cannot be negative.";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("test", 5, -3));
        assertEquals(expectedMessage, exception.getMessage());
    }
    @org.junit.jupiter.api.Test
    void getName_ReturnsCorrectName() {
        String name = "test";
        double speed = 5;
        double distance = 10;
        Horse horse = new Horse(name, speed, distance);

        String actualName = horse.getName();
        assertEquals(name, actualName);
    }

    @org.junit.jupiter.api.Test
    void getSpeed_ReturnsCorrectSpeed() {
        String name = "test";
        double speed = 5;
        double distance = 10;
        Horse horse = new Horse(name, speed, distance);

        double actualSpeed = horse.getSpeed();
        assertEquals(speed, actualSpeed);
    }

    @org.junit.jupiter.api.Test
    void getDistance_ReturnsCorrectDistance() {
        String name = "test";
        double speed = 5;
        double distance = 10;
        Horse horse = new Horse(name, speed, distance);

        double actualDistance = horse.getDistance();
        assertEquals(distance, actualDistance);
    }

    @org.junit.jupiter.api.Test
    void move_CallsGetRandomDoubleMethodWithCorrectParams() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class);){
            Horse horse = new Horse("test", 1, 2);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }

    }
    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.5, 0.8, 15, 0})
    public void move_FormulaIsCorrect(double fakeRandomValue){
        String name = "test";
        double min = 0.2;
        double max = 0.9;
        double speed = 2.5;
        double distance = 250;
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = distance + speed * fakeRandomValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)){
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeRandomValue);
            horse.move();
        }

        assertEquals(expectedDistance, horse.getDistance());
    }
}