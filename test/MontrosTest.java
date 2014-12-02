import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import static org.fest.assertions.Assertions.assertThat;

import models.Monstro;


public class MontrosTest {
	
	@Test
    public void create() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Monstro task = new Monstro("Jubileu");
                task.energia = 12;
                task.save();
                assertThat(task.id).isNotNull();
       
            }
        });
    }

}
