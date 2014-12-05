import org.junit.Test;
import static org.junit.Assert.*;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import static org.fest.assertions.Assertions.assertThat;

import java.util.*;

import models.Monster;
import models.Skill;
import models.Generator;
import models.MonsterGeneratorLink;

public class MonsterGeneratorLinkTest {

    @Test
    public void shouldCreateLink() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                Generator generator = new Generator("Name", 0, 0);
                MonsterGeneratorLink link = new MonsterGeneratorLink(generator, monster);

                // Then
                assertTrue(link != null);
                assertTrue(link.getAmount() > 0);
            } 
        });
    }

    @Test
    public void stuffShouldHappen() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                Generator generator = new Generator("Name", 0, 0);

                // When

                // Then
                assertTrue(true);
            } 
        });
    }



}
