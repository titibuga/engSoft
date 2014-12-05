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

public class MonsterTest {

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
    public void monsterShouldCreateGeneratorLinkOnPurchase() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                Generator generator = new Generator("Name", 0, 0);
                generator.save();
                monster.save();

                // When
                monster.purchase(generator);
                List<MonsterGeneratorLink> list = monster.getGeneratorLinks();

                // Then
                assertTrue(list.size() > 0);
            } 
        });
    }

    @Test
    public void numberOfGeneratorTypesShouldIncreaseOnPurchase() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                Generator generator = new Generator("Name", 0, 0);
                generator.save();
                monster.save();

                // When
                monster.purchase(generator);
                List<Generator> list = monster.getGenerators();

                // Then
                assertTrue(list.size() > 0);
            } 
        });
    }

    @Test
    public void amountOfGeneratorTypesShouldIncreaseOnPurchase() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                Generator generator = new Generator("Name", 0, 0);
                generator.save();
                monster.save();

                // When
                monster.purchase(generator);

                List<Integer> list = monster.getGeneratorsAmounts();
                Iterator<Integer> iter = list.iterator();
                Integer first = iter.next();

                // Then
                assertTrue(first.intValue() > 0);
            } 
        });
    }

    @Test
    public void attributesShouldIncreaseWithTraining() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                monster.setEnergy(10000);

                // When
                monster.trainAttribute(Monster.Attribute.STRENGTH);
                monster.trainAttribute(Monster.Attribute.DEXTERITY);
                monster.trainAttribute(Monster.Attribute.WISDOM);                

                // Then
                assertTrue(monster.getAttribute(Monster.Attribute.STRENGTH) > 0);
                assertTrue(monster.getAttribute(Monster.Attribute.DEXTERITY) > 0);
                assertTrue(monster.getAttribute(Monster.Attribute.WISDOM) > 0);
            } 
        });
    }    

    @Test
    public void attributeCostShouldRaiseWithTraining() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                monster.setEnergy(10000);
                int str = monster.attributeCost(Monster.Attribute.STRENGTH);
                int dex = monster.attributeCost(Monster.Attribute.DEXTERITY);
                int wis = monster.attributeCost(Monster.Attribute.WISDOM);

                // When
                monster.trainAttribute(Monster.Attribute.STRENGTH);
                monster.trainAttribute(Monster.Attribute.DEXTERITY);
                monster.trainAttribute(Monster.Attribute.WISDOM);                

                // Then
                assertTrue(monster.attributeCost(Monster.Attribute.STRENGTH) > str);
                assertTrue(monster.attributeCost(Monster.Attribute.DEXTERITY) > dex);
                assertTrue(monster.attributeCost(Monster.Attribute.WISDOM) > wis);
            } 
        });
    }    

    @Test
    public void energyShouldIncreaseWithAdditions() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                Monster monster = new Monster("Name");
                int energy = monster.getEnergy();

                // When
                monster.addEnergy(100);

                // Then
                assertTrue(monster.getEnergy() > energy);
            } 
        });
    }



}
