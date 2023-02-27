package lyzer.web.tech.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;


public class TestLocalConfig {
    @Test
    public void testCovertInvalidJsonToLocalConfig() {
        String json = "[\"scraperIp\":\"localhost:8080\"]";
        assertThrows(JsonProcessingException.class, () -> {
            LocalConfig.converJSONToLocalConfig(json);
        });
    }

    @Test
    public void testConvertJSONToLocalConfig() {
        try {
            String json = "{\"scraperIp\":\"localhost:8080\",\"scraperVersion\":\"0.1.0\"}";
            LocalConfig localConfig = LocalConfig.converJSONToLocalConfig(json);
            assert localConfig.getScraperIp().equals("localhost:8080");
            assert localConfig.getScraperVersion().equals("0.1.0");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testLocalConfigToString() {
        try {
            String json = "{\"scraperIp\":\"localhost:8080\",\"scraperVersion\":\"0.1.0\"}";
            String expected = "{scraperIp='localhost:8080', scraperVersion='0.1.0'}";
            LocalConfig localConfig = LocalConfig.converJSONToLocalConfig(json);
            assert localConfig.toString().equals(expected);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testLocalConfigGettersAndSetters() {
        LocalConfig localConfig = new LocalConfig();
        localConfig.setScraperIp("localhost:8080");
        assert localConfig.getScraperIp().equals("localhost:8080");
        localConfig.setScraperVersion("0.1.0");
        assert localConfig.getScraperVersion().equals("0.1.0");
    }
}
