package ru.gmdev.comfy_doc.core.application;

import org.thymeleaf.util.StringUtils;
import ru.gmdev.comfy_doc.core.exception.SystemException;
import ru.gmdev.comfy_doc.core.security.UserInfo;
import ru.gmdev.comfy_doc.utils.TypeConverter;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public interface Application {
  String PROPERTY_CONNECTION = "core.db.url";
  String PROPERTY_NCORE_USER = "core.db.user";
  String PROPERTY_NCORE_PASSWORD = "core.db.password";
  String PROPERTY_NCORE_ROLE = "core.db.role";
  String PROPERTY_NCORE_DB_FILE_BLOB_ENABLED = "core.db.file_blob.enabled";
  String PROPERTY_NCORE_DB_FILE_BLOB_DIRECT_READ = "core.db.file_blob.direct.read";
  String PROPERTY_LOCK_TIMEOUT = "core.db.lock.timeout";

  void init(Map<Object, Object> var1);

  void start();

  void stop();

  boolean lock();

  void unlock();

//  default <T> T execute(Context con, SystemRunnable<T> runnable) {
//    SystemRunnableExecuter<T> executer = new SystemRunnableExecuter(this, runnable);
//    executer.prepare(con);
//    return executer.execute();
//  }
//
//  default <T> T execute(ContextSource contextSource, SystemRunnable<T> runnable) {
//    SystemRunnableExecuter<T> executer = new SystemRunnableExecuter(this, runnable);
//    executer.prepare(contextSource.getTransactionalContext());
//    return executer.execute();
//  }
//
//  default <T> T execute(SystemRunnable<T> runnable) {
//    SystemRunnableExecuter<T> executer = new SystemRunnableExecuter(this, runnable);
//    executer.prepare();
//    return executer.execute();
//  }
//
//  default <T> T execute(IsolationLevel isolationLevel, SystemRunnable<T> runnable) {
//    SystemRunnableExecuter<T> executer = new SystemRunnableExecuter(this, runnable);
//    executer.prepare(isolationLevel);
//    return executer.execute();
//  }
//
//  default <T> T executeReadOnly(final SystemRunnable<T> runnable) {
//    return (new ReadOnlyCall<T>(this) {
//      protected T run(Context con) throws SQLException, SystemException {
//        return runnable.run(this.getApplication(), con);
//      }
//    }).execute();
//  }

  String getUserName();

  String getUserRole();

  UserInfo getUserInfo();

  Long getUserId();

  Set<String> getAllowedRoles();

  void setAllowedRoles(Set<String> var1);

  String getUserIP();

  boolean isMfAuth();

  boolean isPlainAuth();

//  CacheManager getCacheManager();

  void setProperties(Map<Object, Object> var1);

  Map<Object, Object> getProperties();

  Object getProperty(Object var1, Object var2);

  Object getProperty(Object var1);

  default boolean getBooleanProperty(String name) {
    return TypeConverter.getBoolean(this.getProperty(name, false));
  }

  default String getStringProperty(Object name) {
    return StringUtils.toString(this.getProperty(name));
  }

  void setProperty(Object var1, Object var2);

  Long getSite();

//  void setConnectionFactory(JdbcConnectionFactory var1);
//
//  JdbcConnectionFactory getConnectionFactory();
//
//  DataObjectFactory getDataObjectFactory();
//
//  MetaObjectCache getMetaObjectCache();
//
//  default MetaObject getMetaObject(String typeName) {
//    return this.getMetaObjectCache().getByName(typeName);
//  }
//
//  SecurityHelper getSecurityHelper();
//
//  DocumentClassHelper getDocumentClassHelper();
//
//  <T> T get(Class<T> var1);
//
//  <T> T remove(Class<T> var1);
//
//  <T> void put(T var1, Class<?> var2);
//
//  default <T> void put(T instance) {
//    this.put(instance, instance.getClass());
//  }
//
//  String getApplicationType();
//
//  DocumentTemplateLoader getTemplateLoader();
//
//  ConnectionManager getConnectionManager();
//
//  long generateSequenceValue(Context var1, String var2);
//
//  void addApplicationListener(Listener<ApplicationEvent> var1);
//
//  void removeApplicationListener(Listener<ApplicationEvent> var1);
//
//  void checkAliveAndReconnect();
//
//  boolean isRunning();
//
//  void createThreadConnectionFactory(String var1) throws SQLException;
//
//  void closeThreadConnectionFactory();
//
//  void addDatabaseEventListener(String var1, EventListener var2);
//
//  void removeDatabaseEventListener(String var1, EventListener var2);
//
//  void performStartupTasks();
//
//  ApplicationHelper getHelper();
//
//  Charset getApplicationCharset();
//
//  MessageEngine getMessageEngine();

  boolean isOptimisticLockMode();

  default boolean isServer() {
    return false;
  }
}
