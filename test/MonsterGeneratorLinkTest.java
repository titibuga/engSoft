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
    public void newLinksShouldNotBeNull() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                MonsterGeneratorLink link;
                Monster monster = new Monster("Name");
                Generator generator = new Generator("Name", 0, 0);
                monster.save();
                generator.save();
                monster.purchase(generator);
                List<MonsterGeneratorLink> list = monster.getGeneratorLinks();
                Iterator<MonsterGeneratorLink> iter = list.iterator();
                link = iter.next();

                // Then
                assertTrue(link != null);
                assertTrue(link.getAmount() > 0);
            } 
        });
    }

    @Test
    public void newGeneratorShouldIncreaseEnergyPerInstant() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                MonsterGeneratorLink link;
                Monster monster = new Monster("Name");
                Generator generator = new Generator("Name", 0, 10);
                monster.save();
                generator.save();
                monster.purchase(generator);
                List<MonsterGeneratorLink> list = monster.getGeneratorLinks();
                Iterator<MonsterGeneratorLink> iter = list.iterator();
                link = iter.next();

                // Then
                assertTrue(link.totalEnergyPerInstant() > 0);
            } 
        });
    }

}
