package lifo.log;

import java.util.Enumeration;
import org.apache.flume.clients.log4jappender.Log4jAppender;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.net.SyslogAppender;

public class CustomLog {
	//单例
	private CustomLog() {
	}
	private static CustomLog log = new CustomLog();
	//创建外部调用静态方法
	public static CustomLog getInstance() {
		return log;
	}
	/**
	 * CustomLogLevel继承log4j类Level
	 * 自定义日志级别
	 */
	private static class CustomLogLevel extends Level {
	
		private static final long serialVersionUID = 1817406486336743660L;

		protected CustomLogLevel(int level, String levelStr, int syslogEquivalent) {
			super(level, levelStr, syslogEquivalent);
		}
	}

	private static final Level LogLevel = new CustomLog.CustomLogLevel(20050, "CUSTOM", SyslogAppender.LOG_LOCAL0);
	/**
	 * 生成日志对象，并设置日志样式
	 * 输出指定日志到文件
	 * @param filePath 输出路径
	 * @param fileName 文件名
	 * @param conversionPattern 输出形式
	 * @param flag 标志符，是否追加
	 * @return logger对象
	 */
	public Logger optionFileCustomLog(String filePath, String fileName, String conversionPattern, boolean flag) {
		//生成logger对象，如存在则返回
		Logger logger = Logger.getLogger(fileName);
		//移除当前所有Appenders 
		logger.removeAllAppenders();
		//设定logger级别
		logger.setLevel(Level.DEBUG);
		//是否继承 父类logger
		logger.setAdditivity(false);
		//生成Appender对象
		FileAppender appender = new RollingFileAppender();
		//log指定输出形式
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern(conversionPattern + "%m%n");
		appender.setLayout(layout);
		//log指定输出路径
		appender.setFile(filePath + fileName + ".log");
		//log指定编码
		appender.setEncoding("UTF-8");
		//是否在已存在log中追加，true追加，false覆盖
		appender.setAppend(flag);
		//当前Appender配置生效
		appender.activateOptions();
		//logger添加Appender对象
		logger.addAppender(appender);
		//返回logger
		return logger;
	}
	 /**
	  * 生成日志对象，并设置日志样式
	 *  输出指定日志到flume
	  * @param LoggerName  logger对象名称
	  * @param hostName 主机名
	  * @param port 主机端口
	  * @param conversionPattern 日志样式
	  * @return logger对象
	  */
	public Logger optionFlumeCustomLog(String LoggerName, String hostName, int port, String conversionPattern) {
		//生成logger对象，如存在则返回
		Logger logger = Logger.getLogger(LoggerName);
		//移除当前所有Appenders 
		logger.removeAllAppenders();
		//设定logger级别
		logger.setLevel(Level.DEBUG);
		//是否继承 父类logger
		logger.setAdditivity(false);
		//生成Appender对象
		Log4jAppender appender = new Log4jAppender();
		//log指定输出形式
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern(conversionPattern + "%m%n");
		appender.setLayout(layout);
		//指定flume主机名
		appender.setHostname(hostName);
		//指定flume端口
		appender.setPort(port);
		appender.setUnsafeMode(true);
		//当前Appender配置生效
		appender.activateOptions();
		//logger添加Appender对象
		logger.addAppender(appender);
		//返回logger
		return logger;
	}
	/**
	 * 调用log方法输出自定义级别的日志
	 * @param logger logger对象
	 * @param object 日志内容
	 */
	public void createCustumLog(Logger logger, Object object) {
		logger.log(LogLevel, object);
	}
	/**
	 * 关闭自定义logger对象
	 * 本项目是web项目，不定时输出日志，不用此方法。
	 * @param logger logger对象
	 */
	public void closeCustomLog(Logger logger) {
		@SuppressWarnings("unchecked")
		Enumeration<Appender> appenders = logger.getAllAppenders();
		while (appenders.hasMoreElements()) {
			Appender appender = (Appender) appenders.nextElement();
			appender.close();
		}
	}
}
