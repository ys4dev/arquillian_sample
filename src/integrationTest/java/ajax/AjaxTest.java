package ajax;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


import java.io.File;
import java.net.URL;

/**
 * Created by sakura on 2014/08/04.
 */
@RunWith(Arquillian.class)
public class AjaxTest {

    private static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "login.war")
                .addClasses(AjaxServlet.class, CandidateSearcher.class, DAO.class)
                .addAsLibraries(new File("/Users/sakura/.gradle/caches/modules-2/files-2.1/org.jumpmind.symmetric.jdbc/mariadb-java-client/1.1.1/d0b477479d2d345ad6365a634220f9f345e1d245/mariadb-java-client-1.1.1.jar"))
                .addAsWebResource(new File(WEBAPP_SRC, "index.html"))
                .addAsWebResource(new File(WEBAPP_SRC, "jquery-2.1.1.js"))
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentUrl;

    @FindBy
    private WebElement name;

    @FindBy
    private WebElement candidate;

    @Test
    public void testAjax() throws Exception {
        browser.get(deploymentUrl.toExternalForm());
        name.sendKeys("か");
        Thread.sleep(1000);
        waitAjax().until().element(candidate).is().present();
        assertThat(candidate.getText(), containsString("か"));
    }
}
