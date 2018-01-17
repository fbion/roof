package org.roof.excel.support;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.roof.excel.config.Column;
import org.roof.excel.config.XslDb;
import org.springframework.util.Assert;

public class PoiExcelMappingWriter extends PoiExcelWriter {
	private XslDb xslDb;
	private ColumnMapping columnMapping;

	@Override
	public void write(List<Map<String, Object>> list) {
		Assert.notNull(xslDb);
		Assert.notNull(xslDb.getColumns());

		if (columnMapping == null) {
			columnMapping = new ColumnMapping(xslDb.getColumns());
		}
		if (this.count == 0) {
			sheetRW.setCurrentRow(0);
			for (Column column : xslDb.getColumns()) {
				super.writeCell(column.getXslcol(), column.getTitle());
			}
			this.count += 1;
		}
		super.write(list);
	}

	@Override
	protected void writeCell(String col, Object val) {
		String c = columnMapping.getXslColumn(col);
		if (StringUtils.isEmpty(c)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("列[" + col + "]找不到映射");
			}
			return;
		}
		super.writeCell(c, val);
	}

	public XslDb getXslDb() {
		return xslDb;
	}

	public void setXslDb(XslDb xslDb) {
		this.xslDb = xslDb;
	}

}
