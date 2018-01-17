import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.entity.Module;
import org.roof.web.resource.service.impl.ResourcesUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 
 * @author liuxin
 * 
 */
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ResourceUtilsTest extends AbstractJUnit4SpringContextTests {
	private ResourcesUtils resourcesUtils;
	private IDaoSupport roofDaoSupport;

	@Test
	public void testInitBasicOperation() {
		Module module = roofDaoSupport.load(Module.class, 1700L);
		resourcesUtils.initBasicOperation(module);
	}

	@Test
	public void exportToFile() throws DaoException, IOException {
		File file = new File("e:\\test.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		resourcesUtils.exportToFile(file);
	}

	@Test
	public void importFromFile() throws DaoException {
		File file = new File("D:/Workspaces/space-roof-3/roof-web-user-service-impl/src/test/java/resourcesv3.txt");
		resourcesUtils.importFromFile(file);

	}

	@Resource
	public void setResourcesUtils(ResourcesUtils resourcesUtils) {
		this.resourcesUtils = resourcesUtils;
	}

	@Resource
	public void setRoofDaoSupport(IDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}
}
