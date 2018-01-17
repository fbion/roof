package org.color.mail.mailuser.service.api;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.color.mail.mailuser.entity.MailUser;
import org.color.mail.mailuser.entity.MailUserVo;
import org.roof.roof.dataaccess.api.Page;
import org.roof.spring.Result;

public interface IMailUserService {

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(MailUser mailUser);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(MailUser mailUser);

	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(MailUser mailUser);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(MailUser mailUser);

	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(MailUser mailUser);

	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(MailUser mailUser);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract MailUserVo load(MailUser mailUser);

	/**
	 * 按对象中的非空属性作为条件，进行查询
	 */
	public abstract List<MailUserVo> selectForList(MailUser mailUser);

	/**
	 * 按对象中的非空属性作为条件，进行分页查询
	 */
	public abstract Page page(Page page, MailUser mailUser);

	/**
	 * 退订
	 * 
	 * @param mailMD5
	 */
	public void cancel(String mailMD5);

	/**
	 * 订阅
	 * 
	 * @param mail
	 */
	public Result subscribe(String mail);

	public Result importByXls(InputStream is);
}