package org.roof.fileupload.alimpl;

import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileInfoService;
import org.roof.fileupload.api.FileNameGenerator;
import org.roof.fileupload.api.FileTypeGenerator;
import org.roof.fileupload.exception.FileInfoNotFoundException;
import org.roof.fileupload.impl.SimpleFileInfo;
import org.roof.fileupload.utils.FileUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcFileInfoService implements FileInfoService {
	private static final String DEFAULT_LOAD_BY_NAME_SQL = "select * from fileinfo where name = ?";
	private static final String DEFAULT_DELETE_BY_NAME_SQL = "delete from fileinfo where name = ?";
	private static final String DEFAULT_SAVE_SQL = "insert into fileinfo(displayName,name,fileSize,realPath,webPath,type) values(?,?,?,?,?,?)";
	private String loadByNameSql = DEFAULT_LOAD_BY_NAME_SQL;
	private String deleteByNameSql = DEFAULT_DELETE_BY_NAME_SQL;
	private String saveSql = DEFAULT_SAVE_SQL;
	private FileNameGenerator fileNameGenerator;
	private JdbcTemplate jdbcTemplate;
	private FileTypeGenerator fileTypeGenerator;

	@Override
	public FileInfo loadByName(final String name)
			throws FileInfoNotFoundException {
		FileInfo fileInfo = jdbcTemplate.queryForObject(loadByNameSql,
				new Object[] { name }, new RowMapper<FileInfo>() {

					@Override
					public FileInfo mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						SimpleFileInfo fileInfo = new SimpleFileInfo();
						fileInfo.setId(rs.getLong("id"));
						fileInfo.setName(rs.getString("name"));
						fileInfo.setDisplayName(rs.getString("displayName"));
						fileInfo.setFileSize(rs.getLong("fileSize"));
						fileInfo.setRealPath(rs.getString("realPath"));
						fileInfo.setWebPath(rs.getString("webPath"));
						fileInfo.setType(rs.getString("type"));
						return fileInfo;
					}
				});
		if (fileInfo == null) {
			throw new FileInfoNotFoundException("name:" + name
					+ "fileinfo not found");
		}
		return fileInfo;
	}

	@Override
	public int deleteByName(String name) throws FileInfoNotFoundException {
		return jdbcTemplate.update(deleteByNameSql, new Object[] { name });
	}

	@Override
	public FileInfo createFileInfo(Map<String, Object> xdata) {
		String displayName = ObjectUtils.toString(xdata.get("displayName"), "");
		FileInfo fileInfo = new SimpleFileInfo();
		fileInfo.setName(fileNameGenerator.getFileName()
				+ FileUtils.getExtention(displayName));
		fileInfo.setDisplayName(displayName);
		fileInfo.setType(fileTypeGenerator.getFileType(displayName));
		fileInfo.setFileSize((long) xdata.get("fileSize"));
		return fileInfo;
	}

	@Override
	public FileInfo saveFileInfo(FileInfo fileInfo) {
		jdbcTemplate.update(saveSql, fileInfo.getDisplayName(),
				fileInfo.getName(), fileInfo.getFileSize(),
				fileInfo.getRealPath(), fileInfo.getWebPath(),
				fileInfo.getType());
		return fileInfo;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getLoadByNameSql() {
		return loadByNameSql;
	}

	public String getDeleteByNameSql() {
		return deleteByNameSql;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setLoadByNameSql(String loadByNameSql) {
		this.loadByNameSql = loadByNameSql;
	}

	public void setDeleteByNameSql(String deleteByNameSql) {
		this.deleteByNameSql = deleteByNameSql;
	}

	public FileNameGenerator getFileNameGenerator() {
		return fileNameGenerator;
	}

	public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
		this.fileNameGenerator = fileNameGenerator;
	}

	public String getSaveSql() {
		return saveSql;
	}

	public void setSaveSql(String saveSql) {
		this.saveSql = saveSql;
	}

	public FileTypeGenerator getFileTypeGenerator() {
		return fileTypeGenerator;
	}

	public void setFileTypeGenerator(FileTypeGenerator fileTypeGenerator) {
		this.fileTypeGenerator = fileTypeGenerator;
	}

}
