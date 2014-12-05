import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import controllers.Application;
import models.Monster;
import models.Skill;
import models.Generator;
import models.MonsterGeneratorLink;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void applicationShouldRenderContent() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }

    @Test
    public void skillShopShouldCreateInitialSkills() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                List<Skill> skills;
                Monster monster = new Monster();
                monster.save();

                // When
                Application.skillShop(monster.getId());
                skills = Skill.find.all();

                // Then
                assertTrue(skills.size() > 0);
            } 
        });
    }

    @Test
    public void generatorShopShouldCreateInitialGenerators() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given
                List<Generator> generators;
                Monster monster = new Monster();
                monster.save();

                // When
                Application.generatorShop(monster.getId());
                generators = Generator.find.all();

                // Then
                assertTrue(generators.size() > 0);
            } 
        });
    }

    @Test
    public void stuffShouldHappen() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                // Given

                // When

                // Then
                assertTrue(true);
            } 
        });
    }    
}
