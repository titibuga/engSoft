import org.junit.Test;
import static org.junit.Assert.*;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import static org.fest.assertions.Assertions.assertThat;

import java.util.*;

import models.Monster;
import models.Skill;

public class MonsterTest {
	
	@Test
    public void create() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Monster task = new Monster("Jubileu");
                task.setEnergy(42);
                task.save();
                assertThat(task.id).isNotNull();
            } 
        });
    }

    @Test
    public void monsterShouldNotPurchaseExpensiveSkills() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Skill expensiveSkill = new Skill("Name", 100, 100);
                Monster monster = new Monster("Name");
                monster.setEnergy(1);

                // When
                boolean expensiveResult = monster.mayPurchase(expensiveSkill);
                
                // Then
                assertFalse(expensiveResult);
            } 
        });
    }
    @Test
    public void monsterShouldNotPurchaseStrongSkills() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Skill strengthSkill = new Skill("Name", 100, 100);
                Skill wisdomSkill = new Skill("Name", 100, 100);
                Skill dexteritySkill = new Skill("Name", 100, 100);

                Monster monster = new Monster("Name");
                monster.setEnergy(1);

                // When
                boolean strengthResult = monster.mayPurchase(strengthSkill);
                boolean wisdomResult = monster.mayPurchase(wisdomSkill);
                boolean dexterityResult = monster.mayPurchase(dexteritySkill);

                // Then
                assertFalse(strengthResult);
                assertFalse(wisdomResult);
                assertFalse(dexterityResult);
            } 
        });
    }

    @Test
    public void monsterShouldNotPurchaseRepeatedSkills() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Skill repeatedSkill = new Skill("Name", 0, 0);

                Monster monster = new Monster("Name");
                monster.setEnergy(1);

                // When
                monster.purchase(repeatedSkill);
                boolean repeatedResult = monster.mayPurchase(repeatedSkill);

                // Then
                assertFalse(repeatedResult);
            } 
        });
    }

    @Test
    public void monsterShouldPurchaseOKSkills() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                monster.setEnergy(100);

                Skill skill = new Skill("Name", 10 , 10);

                // When
                boolean result = monster.purchase(skill);
                List<Skill> inventory = monster.getInventory();

                // Then
                assertTrue(result);
                assertTrue(inventory.size() > 0);
            } 
        });
    }

    @Test
    public void monsterShouldPayForSkills() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                monster.setEnergy(100);

                Skill skill = new Skill("Name", 10 , 10);

                // When
                boolean result = monster.purchase(skill);

                // Then
                assertTrue(monster.getEnergy() < 100);
            } 
        });
    }

    @Test
    public void monsterShouldHavePurchasedSkill() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                Skill skill = new Skill ("Name", 0, 0);

                // When
                monster.purchase(skill);

                // Then
                assertTrue(monster.hasSkill(skill));
            } 
        });
    }

    @Test
    public void stuffShouldHappen() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");

                // When

                // Then
                assertTrue(true);
            } 
        });
    }


}
