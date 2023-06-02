package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryMock;
  private TorpedoStore secondaryMock;

  @BeforeEach
  public void init(){
    primaryMock = mock(TorpedoStore.class);
    secondaryMock = mock(TorpedoStore.class);

    this.ship = new GT4500(primaryMock, secondaryMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    // Verify
    verify(primaryMock, times(1)).isEmpty();
    verify(primaryMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(false);
    when(primaryMock.fire(1)).thenReturn(true);

    when(secondaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    // Verify
    verify(primaryMock, times(1)).isEmpty();
    verify(primaryMock, times(1)).fire(1);
    verify(secondaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Fail_BothEmpty(){
    // arrange
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertFalse(result);

    // Verify
    verify(primaryMock, times(1)).isEmpty();
    verify(primaryMock, times(0)).fire(1);
    verify(secondaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_PrimaryIsEmpty(){
    // Arrange
    when(primaryMock.isEmpty()).thenReturn(true);
    when(secondaryMock.isEmpty()).thenReturn(false);
    when(secondaryMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertTrue(result);

    // Verify
    verify(primaryMock, times(1)).isEmpty();
    verify(primaryMock, times(0)).fire(1);
    verify(secondaryMock, times(1)).isEmpty();
    verify(secondaryMock, times(1)).fire(1);
  }
}
