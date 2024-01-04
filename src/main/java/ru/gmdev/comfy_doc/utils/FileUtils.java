package ru.gmdev.comfy_doc.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import org.springframework.security.crypto.codec.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FileUtils {
  private static final int BUFF_SIZE = 131072;
  private static final File TEMP_DIR = new File(System.getProperty("java.io.tmpdir"));
  public static final String defaultCharsetName = System.getProperty("file.encoding");
  private static final Set<Character> allowSymbolSet;
  private static final Set<Character> validSymbolSet;

  private FileUtils() {
  }

  public static String extractFileExt(String fileName) {
    String name = extractFileName(fileName);
    if (name == null) {
      return null;
    } else {
      int k = name.lastIndexOf(46);
      return k == -1 ? null : name.substring(k + 1);
    }
  }

  public static String extractFileName(String filePath) {
    return StringUtils.isEmpty(filePath) ? null : (new File(filePath)).getName();
  }

  public static String extractFilePath(String filePath) {
    if (StringUtils.isEmpty(filePath)) {
      return null;
    } else {
      File parent = (new File(filePath)).getParentFile();
      return parent == null ? null : parent.getAbsolutePath();
    }
  }

  public static long copy(InputStream src, OutputStream dest, long maxSize) throws IOException {
    if (maxSize == 0L) {
      return 0L;
    } else {
      int packetSize = maxSize > 0L && 131072L > maxSize ? (int)maxSize : 131072;
      byte[] buf = new byte[packetSize];
      long res = 0L;

      do {
        int len = src.read(buf, 0, maxSize > 0L && (long)packetSize + res > maxSize ? (int)(maxSize - res) : packetSize);
        if (len < 0) {
          break;
        }

        dest.write(buf, 0, len);
        res += (long)len;
      } while(maxSize <= 0L || res < maxSize);

      return res;
    }
  }

  public static void copy(String src, String dest) throws IOException {
    copy(new File(src), new File(dest));
  }

  public static void copy(File sourcefile, File destfile) throws IOException {
    copy(sourcefile, destfile, true);
  }

  public static void copy(File sourcefile, File destfile, boolean overwrite) throws IOException {
    if (!destfile.exists() || overwrite) {
      if (!destfile.exists() && destfile.getParentFile() != null) {
        destfile.getParentFile().mkdirs();
      }

      destfile.createNewFile();
      FileOutputStream out = null;
      FileInputStream in = null;

      try {
        out = new FileOutputStream(destfile, false);
        in = new FileInputStream(sourcefile);
        copy(in, out, -1L);
      } finally {
        if (out != null) {
          out.close();
        }

        if (in != null) {
          in.close();
        }

      }

    }
  }

  public static int getBytesFromStream(InputStream is, byte[] buf) throws IOException {
    int c = is.read(buf);
    int offset = c;

    while(c >= 0 && offset != buf.length) {
      c = is.read(buf, offset, buf.length - offset);
      if (c > 0) {
        offset += c;
      }
    }

    return offset;
  }

  public static byte[] getBytesFromStream(InputStream is, int maxSize) throws IOException {
    if (is == null) {
      return null;
    } else {
      byte[] buf = new byte[4096];
      byte[] data = new byte[buf.length];
      int size = 0;

      do {
        int c = is.read(buf);
        if (c < 0) {
          break;
        }

        if (maxSize > -1 && size + c > maxSize) {
          c = maxSize - size;
        }

        size += c;
        if (data.length < size) {
          byte[] b = new byte[data.length << 1];
          System.arraycopy(data, 0, b, 0, data.length);
          data = b;
        }

        System.arraycopy(buf, 0, data, size - c, c);
      } while(maxSize <= -1 || size < maxSize);

      return Arrays.copyOf(data, size);
    }
  }

  public static byte[] getBytesFromFile(File file, int offset, int maxSize) throws IOException {
    InputStream is = new FileInputStream(file);
    Throwable var4 = null;

    byte[] var7;
    try {
      long length = Math.min(file.length() - (long)offset, (long)maxSize);
      var7 = getBytesFromStream(is, offset, length);
    } catch (Throwable var16) {
      var4 = var16;
      throw var16;
    } finally {
      if (is != null) {
        if (var4 != null) {
          try {
            is.close();
          } catch (Throwable var15) {
            var4.addSuppressed(var15);
          }
        } else {
          is.close();
        }
      }

    }

    return var7;
  }

  public static byte[] getBytesFromStream(InputStream is, int offset, long maxSize) throws IOException {
    if (is.skip((long)offset) != (long)offset) {
      return new byte[0];
    } else {
      assert maxSize <= 2147483647L;

      byte[] bytes = new byte[(int)maxSize];

      int currentPos;
      int numRead;
      for(currentPos = 0; currentPos < bytes.length && (numRead = is.read(bytes, currentPos, bytes.length - currentPos)) >= 0; currentPos += numRead) {
      }

      return currentPos == bytes.length ? bytes : Arrays.copyOf(bytes, currentPos);
    }
  }

  public static byte[] getBytesFromFile(File file) throws IOException {
    return getBytesFromFile(file, 0, 2147483647);
  }

  public static File[] getFilesByPattern(String pattern, File dir) {
    final Pattern p = Pattern.compile(pattern);
    return getFilesByFilter(new FileFilter() {
      public boolean accept(File pathname) {
        Matcher m = p.matcher(pathname.getName());
        return !pathname.isDirectory() && m.matches();
      }
    }, dir);
  }

  public static File[] getFilesByFilter(FileFilter filter, File dir) {
    if (dir != null && dir.isDirectory()) {
      return filter == null ? dir.listFiles() : dir.listFiles(filter);
    } else {
      return new File[0];
    }
  }

  public static String getFileNameWithoutExtension(String fileName) {
    if (StringUtils.isEmpty(fileName)) {
      return fileName;
    } else {
      int index = fileName.lastIndexOf(46);
      return index == -1 ? fileName : fileName.substring(0, index);
    }
  }

  public static String tail(String fileName, int n) {
    if (fileName != null && n != 0) {
      ArrayBlockingQueue aQueue = new ArrayBlockingQueue(n);

      try {
        BufferedReader r = new BufferedReader(new FileReader(fileName));

        try {
          for(String s = r.readLine(); s != null; s = r.readLine()) {
            if (aQueue.remainingCapacity() == 0) {
              aQueue.poll();
            }

            aQueue.put(s + "\n");
          }
        } finally {
          r.close();
        }
      } catch (Exception var9) {
      }

      StringBuilder sb = new StringBuilder(n - aQueue.remainingCapacity());
      Iterator var11 = aQueue.iterator();

      while(var11.hasNext()) {
        String str = (String)var11.next();
        sb.append(str);
      }

      return sb.toString();
    } else {
      return null;
    }
  }

//  public static String readToString(String fileName) throws IOException {
//    return readToString(fileName, CharsetUtils.defaultCharsetName);
//  }
//
//  public static String readToString(String fileName, int maxSize) throws IOException {
//    return readToString(fileName, CharsetUtils.defaultCharsetName, maxSize);
//  }

  public static String readToString(String fileName, String encoding, int maxSize) throws IOException {
    FileInputStream is = new FileInputStream(fileName);
    return readToString((InputStream)is, encoding, maxSize);
  }

  public static String readToString(String fileName, String encoding) throws IOException {
    FileInputStream is = new FileInputStream(fileName);
    return readToString((InputStream)is, encoding);
  }
//
//  public static String readToString(InputStream is) throws IOException {
//    return readToString(is, CharsetUtils.defaultCharsetName);
//  }
//
//  public static String readToString(InputStream is, int maxSize) throws IOException {
//    return readToString(is, CharsetUtils.defaultCharsetName, maxSize);
//  }

  public static String readToString(InputStream is, String encoding) throws IOException {
    return readToString((InputStream)is, encoding, -1);
  }

  public static String readToString(InputStream is, String encoding, int maxSize) throws IOException {
    char[] buf = new char[4096];
    BufferedReader in = new BufferedReader(new InputStreamReader(is, encoding));

    try {
      StringBuilder sb = new StringBuilder();

      do {
        int c = in.read(buf);
        if (c < 0) {
          String var11 = sb.toString();
          return var11;
        }

        sb.append(buf, 0, c);
      } while(maxSize <= 0 || sb.length() < maxSize);

      sb.setLength(maxSize);
      String var7 = sb.toString();
      return var7;
    } finally {
      in.close();
    }
  }

  public static String replaceFileExtention(String fileName, String aNewExtention) {
    int index = fileName.lastIndexOf(46);
    if (index > 0 && !fileName.substring(index).contains("/") && !fileName.substring(index).contains("\\")) {
      fileName = fileName.substring(0, index);
    }

    return fileName + "." + aNewExtention;
  }

  public static long writeStream(InputStream src, File dest, boolean append) throws IOException {
    FileOutputStream f = new FileOutputStream(dest, append);

    long var4;
    try {
      var4 = pipe(src, f);
    } finally {
      f.close();
    }

    return var4;
  }

  public static long writeStream(InputStream src, File dest) throws IOException {
    return writeStream(src, dest, false);
  }

  public static void writeBytes(File file, byte[] data, boolean append) throws IOException {
    FileOutputStream os = new FileOutputStream(file, append);

    try {
      os.write(data);
    } finally {
      os.close();
    }

  }

  public static void writeBytes(OutputStream os, byte[] data, int offset, int len, int maxBufSize) throws IOException {
    int n;
    for(int rem = len; rem > 0; rem -= n) {
      n = Math.min(rem, maxBufSize);
      os.write(data, len - rem + offset, n);
    }

  }
//
//  public static void writeString(String fileName, String s, boolean append) throws IOException {
//    writeString(fileName, s, append, CharsetUtils.defaultCharsetName);
//  }

  public static void writeString(String fileName, String s, boolean append, String encoding) throws IOException {
    FileOutputStream os = new FileOutputStream(fileName, append);
    writeString(os, s, encoding);
  }

  public static void writeString(OutputStream os, String s, String encoding) throws IOException {
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, encoding));

    try {
      out.write(s);
    } finally {
      out.close();
    }

  }

  public static void deleteList(String listFileName, String baseDir) throws Exception {
    if ((new File(listFileName)).exists()) {
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(listFileName), defaultCharsetName));

      try {
        while(in.ready()) {
          File f = new File(baseDir + File.separator + in.readLine());
          if (f.exists() && !f.delete()) {
            throw new Exception("Cannot delete file \"" + f.getAbsolutePath() + "\"");
          }
        }
      } finally {
        in.close();
      }
    }

  }

  public static void cleanDir(String path) throws IOException {
    File[] sd = (new File(path)).listFiles();
    if (sd != null) {
      File[] var2 = sd;
      int var3 = sd.length;

      for(int var4 = 0; var4 < var3; ++var4) {
        File aSd = var2[var4];
        if (aSd.isFile() && !aSd.delete()) {
          throw new IOException("Cannot delete file \"" + aSd.getAbsolutePath() + "\"");
        }
      }
    }

  }

  public static boolean deleteDir(String dir) throws IOException {
    return deleteDir(new File(dir));
  }

  public static boolean deleteDir(File dir) throws IOException {
    if (!dir.exists()) {
      return true;
    } else {
      cleanPath(dir.getAbsolutePath());
      return dir.delete();
    }
  }

  public static void cleanPath(String path) throws IOException {
    Collection<File> files = listFilesRecursive(new File(path), new ArrayList(), true);
    Iterator var2 = files.iterator();

    File file;
    do {
      if (!var2.hasNext()) {
        return;
      }

      file = (File)var2.next();
    } while(file.delete());

    throw new IOException("Cannot delete file \"" + file.getAbsolutePath() + "\"");
  }

  public static void copyDir(String source, String destination, boolean recursive) throws IOException {
    copyDir(source, destination, recursive, true);
  }

  public static void copyDir(String source, String destination, boolean recursive, boolean overwrite) throws IOException {
    copyDir(new File(source), destination, recursive, overwrite, (List)null);
  }

  public static void copyDir(File source, String destination, boolean recursive, boolean overwrite, List<File[]> copiedFiles) throws IOException {
    File[] sd = source.listFiles();
    (new File(destination)).mkdirs();
    File[] var6 = sd;
    int var7 = sd.length;

    for(int var8 = 0; var8 < var7; ++var8) {
      File aSd = var6[var8];
      if (aSd.isDirectory()) {
        if (recursive) {
          copyDir(aSd, destination + File.separator + aSd.getName(), true, overwrite, copiedFiles);
        }
      } else {
        File dest = new File(constructPath(destination, aSd.getName()));
        copy(aSd, dest, overwrite);
        if (copiedFiles != null) {
          copiedFiles.add(new File[]{aSd, dest});
        }
      }
    }

  }

  public static boolean moveDirContent(File source, String destination) throws IOException {
    File[] sd = source.listFiles();
    (new File(destination)).mkdirs();
    boolean res = true;
    File[] var4 = sd;
    int var5 = sd.length;

    for(int var6 = 0; var6 < var5; ++var6) {
      File file = var4[var6];
      File dest = new File(destination + File.separator + file.getName());
      res &= file.renameTo(dest);
    }

    return res;
  }

  public static List<File> listFilesRecursiveByMask(File path, List<File> list, String mask) {
    Pattern fileMask = getPattern(mask);
    return listFilesRecursiveByMask(path, list, fileMask);
  }

  public static List<File> listFilesRecursiveByMask(File path, List<File> list, Pattern pattern) {
    File[] sd = path.listFiles();
    if (sd != null) {
      File[] var4 = sd;
      int var5 = sd.length;

      for(int var6 = 0; var6 < var5; ++var6) {
        File aSd = var4[var6];
        if (aSd.isDirectory()) {
          list = listFilesRecursiveByMask(aSd, list, pattern);
        } else if (pattern.matcher(aSd.getName()).matches()) {
          list.add(0, aSd);
        }
      }
    }

    return list;
  }

  public static List<File> listFilesByMask(File path, String mask, boolean acceptDirs) {
    Pattern pattern = getPattern(mask);
    return listFilesByMask(path, acceptDirs, pattern);
  }

  public static List<File> listFilesByMask(File path, boolean acceptDirs, Pattern pattern) {
    List<File> list = new ArrayList();
    File[] sd = path.listFiles();
    if (sd != null) {
      File[] var5 = sd;
      int var6 = sd.length;

      for(int var7 = 0; var7 < var6; ++var7) {
        File aSd = var5[var7];
        if ((!aSd.isDirectory() || acceptDirs) && pattern.matcher(aSd.getName()).matches()) {
          list.add(0, aSd);
        }
      }
    }

    return list;
  }

  public static List<File> listFilesRecursive(File path, List<File> list, boolean includeDirectories) {
    File[] sd = path.listFiles();
    if (sd != null) {
      File[] var4 = sd;
      int var5 = sd.length;

      for(int var6 = 0; var6 < var5; ++var6) {
        File aSd = var4[var6];
        if (aSd.isDirectory()) {
          if (includeDirectories) {
            list.add(0, aSd);
          }

          list = listFilesRecursive(aSd, list, includeDirectories);
        } else {
          list.add(0, aSd);
        }
      }
    }

    return list;
  }

  public static File[] listFilesRecursive(File path, boolean includeDirectories) {
    Collection<File> files = listFilesRecursive(path, new ArrayList(), includeDirectories);
    return files.size() == 0 ? null : (File[])files.toArray(new File[files.size()]);
  }
//
//  public static void processFilesInDir(File dir, boolean recursively, Filter<File> filter) {
//    try {
//      processFilesInDir(dir, recursively, filter, false);
//    } catch (FileUtils.StopProcessingException var4) {
//    }
//
//  }

//  public static void processFilesInDir(File dir, boolean recursively, Filter<File> filter, boolean stopOnFilterFailure) throws FileUtils.StopProcessingException {
//    processFilesInDir(dir, recursively, filter, (Comparator)null, stopOnFilterFailure);
//  }
//
//  public static void processFilesInDir(File dir, boolean recursively, Filter<File> filter, Comparator<File> sorter, boolean stopOnFilterFailure) throws FileUtils.StopProcessingException {
//    File[] files = dir.listFiles();
//    if (files != null) {
//      if (sorter != null) {
//        Arrays.sort(files, sorter);
//      }
//
//      File[] var6 = files;
//      int var7 = files.length;
//
//      for(int var8 = 0; var8 < var7; ++var8) {
//        File file = var6[var8];
//        if (!filter.filter(file) && stopOnFilterFailure) {
//          throw new FileUtils.StopProcessingException();
//        }
//
//        if (file.isDirectory() && recursively) {
//          processFilesInDir(file, true, filter, sorter, stopOnFilterFailure);
//        }
//      }
//    }
//
//  }

  public static String convertMaskToRegExp(String file_mask) {
    return file_mask.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*").replaceAll("\\?", ".");
  }

  public static String convertFileMaskToRegExp(String file_mask) {
    return convertMaskToRegExp(file_mask.toUpperCase());
  }

  public static Pattern getPattern(String fileMask) {
    return Pattern.compile(convertMaskToRegExp(fileMask));
  }

  public static boolean exists(String fileName) {
    return (new File(fileName)).exists();
  }

//  public static InputStream getResourceInpuStream(String fileName) throws FileNotFoundException {
//    if (exists(fileName)) {
//      return new FileInputStream(fileName);
//    } else {
//      String urlFileName = normalizeResourceFileName(fileName);
//
//      try {
//        Iterator var2 = ServiceProvider.getServices(FileResourceInputStreamService.class).iterator();
//
//        while(var2.hasNext()) {
//          FileResourceInputStreamService sp = (FileResourceInputStreamService)var2.next();
//          InputStream resource = sp.getResource(urlFileName);
//          if (resource != null) {
//            return resource;
//          }
//        }
//      } catch (DuplicateServiceException var5) {
//        throw new RuntimeException("Error loading services for " + FileResourceInputStreamService.class.getName(), var5);
//      }
//
//      InputStream is = FileUtils.class.getResourceAsStream("/" + urlFileName);
//      if (is != null) {
//        return is;
//      } else {
//        throw new FileNotFoundException("File with name \"" + fileName + "\" not found.");
//      }
//    }
//  }

  public static String normalizeResourceFileName(String fileName) {
    fileName = Paths.get(fileName).normalize().toString();
    return fileName.replaceAll("\\\\", "/");
  }

  public static boolean makeDirsForFile(String file) {
    return (new File(file)).getParentFile().mkdirs();
  }

  public static String constructPath(String... path) {
    if (path != null && path.length != 0) {
      StringBuilder fullPath = new StringBuilder();
      String[] var2 = path;
      int var3 = path.length;

      for(int var4 = 0; var4 < var3; ++var4) {
        String loc = var2[var4];
        if (!StringUtils.isEmpty(loc)) {
          if (fullPath.length() == 0) {
            fullPath.append(loc);
          } else {
            char c = fullPath.charAt(fullPath.length() - 1);
            char c1 = loc.charAt(0);
            if (c != '\\' && c != '/' && c1 != '\\' && c1 != '/') {
              fullPath.append('/');
            }

            fullPath.append(loc);
          }
        }
      }

      return fullPath.toString();
    } else {
      return "";
    }
  }

  /** @deprecated */
  @Deprecated
  public static long pipe(InputStream in, OutputStream out) throws IOException {
    byte[] buf = new byte[1024];

    int k;
    long res;
    for(res = 0L; (k = in.read(buf, 0, 1024)) > -1; res += (long)k) {
      out.write(buf, 0, k);
    }

    return res;
  }

//  public static String readTextFiltered(File file, Filter<String> filter) throws IOException {
//    FileInputStream is = new FileInputStream(file);
//
//    String var3;
//    try {
//      var3 = readTextFiltered((InputStream)is, filter);
//    } finally {
//      is.close();
//    }
//
//    return var3;
//  }
//
//  public static String readTextFiltered(InputStream is, Filter<String> filter) throws IOException {
//    BufferedReader in = new BufferedReader(new InputStreamReader(is));
//    StringBuilder buf = null;
//
//    while(true) {
//      String line;
//      do {
//        line = in.readLine();
//        if (line == null) {
//          if (buf != null) {
//            return buf.toString();
//          }
//
//          return null;
//        }
//      } while(filter != null && !filter.filter(line));
//
//      if (buf != null) {
//        buf.append("\n");
//      } else {
//        buf = new StringBuilder();
//      }
//
//      buf.append(line);
//    }
//  }

  public static void deleteFile(String fileName) throws IOException {
    File f = new File(fileName);
    deleteFile(f);
  }

  public static void deleteFile(File file) throws IOException {
    if (file != null) {
      if (file.exists() && !file.delete()) {
        throw new IOException("Can't delete file \"" + file.getAbsolutePath() + "\" for unknown reason.");
      }
    }
  }

  public static void deleteFiles(List<File> files) throws IOException {
    Iterator var1 = files.iterator();

    while(var1.hasNext()) {
      File file = (File)var1.next();
      deleteFile(file);
    }

  }

  public static InputStream openFiles(final List<File> files, final boolean deleteOnClose) throws IOException {
    final ArrayList list = new ArrayList(files.size());

    try {
      Iterator var3 = files.iterator();

      while(var3.hasNext()) {
        File file = (File)var3.next();
        list.add(new FileInputStream(file));
      }
    } catch (IOException var5) {
      closeAll(list);
      throw var5;
    }

    return new SequenceInputStream(Collections.enumeration(list)) {
      public void close() throws IOException {
        super.close();
        FileUtils.closeAll(list);
        if (deleteOnClose) {
          FileUtils.deleteFiles(files);
        }

      }
    };
  }

  private static void closeAll(List<InputStream> list) {
    Iterator var1 = list.iterator();

    while(var1.hasNext()) {
      InputStream is = (InputStream)var1.next();

      try {
        is.close();
      } catch (IOException var4) {
      }
    }

  }

  public static File generateNewFileName(File file, int startNumber) {
    return generateNewFileName(file, startNumber, false);
  }

  public static File generateNewFileName(File file, int startNumber, boolean ignoreFileExtension) {
    if (!file.exists() && startNumber == 0) {
      return file;
    } else {
      String filePath = file.getParentFile().getPath();
      String fileName = file.getName();
      String fileExt;
      int i;
      if (ignoreFileExtension) {
        fileExt = "";
      } else {
        i = fileName.indexOf(46);
        fileExt = i > -1 ? fileName.substring(i) : "";
        fileName = fileName.substring(0, fileName.length() - fileExt.length());
      }

      int j = 0;
      String[] var10002 = new String[]{filePath, null};
      StringBuilder var10005 = (new StringBuilder()).append(fileName).append("[");
      j = j + 1;
      var10002[1] = var10005.append(j + startNumber).append("]").append(fileExt).toString();
      file = new File(constructPath(var10002));

      while(file.exists()) {
        file = new File(constructPath(filePath, fileName + "[" + (j++ + startNumber) + "]" + fileExt));
        if (j > 100) {
          fileName = fileName + UUID.randomUUID();
          file = new File(constructPath(filePath, fileName + fileExt));
          j = 1;
        }
      }

      return file;
    }
  }

  public static void copyOrHardlink(File source, File dest) throws IOException {
    copy(source, dest);
  }

  public static void renameOrCopyAndDelete(File src, File dest) throws IOException {
    if (dest.exists()) {
      throw new IOException("File \"" + dest.getAbsolutePath() + "\" already exists.");
    } else {
      File dir = dest.getParentFile();
      if (dir != null) {
        dir.mkdirs();
      }

      if (!src.renameTo(dest)) {
        copy(src, dest);
        deleteFile(src);
      }

    }
  }

  public static File createTempFile(String prefix, String suffix) throws IOException {
    return createTempFile(prefix, suffix, TEMP_DIR);
  }

  public static File createTempFile(String prefix, String suffix, File dir) throws IOException {
    if (StringUtils.isEmpty(prefix) || prefix.length() < 3) {
      prefix = StringUtils.alignStringRight(StringUtils.nvl(prefix, ""), 3, '_').toString();
    }

    return File.createTempFile(prefix, suffix, dir);
  }

  public static File constructTempPath(String fileName) {
    return new File(constructTempPathName(fileName));
  }

  public static String constructTempPathName(String fileName) {
    return constructPath(getTempDirPath(), fileName);
  }

  public static File getTempDir() {
    return TEMP_DIR;
  }

  public static String getTempDirPath() {
    return TEMP_DIR.getAbsolutePath();
  }

  public static File getTopParent(File file) {
    for(File parentFile = file; parentFile != null; parentFile = parentFile.getParentFile()) {
      file = parentFile;
    }

    return file;
  }

  public static void copy(InputStream is, File file) throws IOException {
    copy(is, file, false);
  }

  public static void copy(InputStream is, File file, boolean append) throws IOException {
    FileOutputStream os = new FileOutputStream(file, append);

    try {
      copy(is, os, -1L);
    } finally {
      os.close();
    }

  }

  public static boolean containsInvalidChars(String key) {
    if (key == null) {
      return false;
    } else {
      String str = key.toLowerCase();

      for(int i = 0; i < key.length(); ++i) {
        char c = str.charAt(i);
        if (!validSymbolSet.contains(c)) {
          return true;
        }
      }

      return false;
    }
  }

  public static String encodeFileName(String key) {
    if (key == null) {
      return null;
    } else {
      StringBuilder buf = new StringBuilder(key.length());
      String str = key.toLowerCase();

      for(int i = 0; i < key.length(); ++i) {
        if (allowSymbolSet.contains(str.charAt(i))) {
          buf.append(key.charAt(i));
        } else {
          buf.append("#").append(String.valueOf(key.charAt(i))).append("#");
        }
      }

      return buf.toString();
    }
  }

  public static String encodeFilePath(String path) {
    if (path == null) {
      return null;
    } else {
      File file = new File(path);

      StringBuilder buf;
      for(buf = new StringBuilder(path.length()); file != null; file = file.getParentFile()) {
        String name = file.getName();
        if (buf.length() > 0) {
          buf.insert(0, File.separatorChar);
        }

        buf.insert(0, encodeFileName(name));
      }

      return buf.toString();
    }
  }

  public static String decodeFileName(String fileName) {
    int indexStart;
    for(int position = 0; (indexStart = fileName.indexOf(35, position)) > -1; position = indexStart + 1) {
      int indexFinish = fileName.indexOf(35, indexStart + 1);
      if (indexFinish == -1) {
        throw new RuntimeException("Can't decode file name");
      }

      char symbol = (char)Integer.parseInt(fileName.substring(indexStart + 1, indexFinish));
      fileName = fileName.substring(0, indexStart).concat(new String(new char[]{symbol})).concat(fileName.substring(indexFinish + 1));
    }

    return fileName;
  }

  public static void decodeBase64(File src, File dst) throws IOException {
    String base64 = readToString(src.getAbsolutePath(), defaultCharsetName);
    writeBytes(dst, Base64.decode(base64.getBytes()), false);
  }

  public static File getUserHomeDir() {
    return new File(System.getProperty("user.home"));
  }

  static {
    String dir;
    if (!TEMP_DIR.exists() && !TEMP_DIR.mkdirs()) {
      try {
        dir = TEMP_DIR.getCanonicalPath();
      } catch (IOException var2) {
        dir = TEMP_DIR.getAbsolutePath();
      }

      System.out.println("Can't access temporary directory \"" + StringUtils.nvl(dir, "") + "\".");
    }

    allowSymbolSet = new HashSet();
    dir = "zxcvbnmasdfghjklqwertyuiopячсмитьбюэждлорпавыфйцукенгшщзхъё1234567890 -(){}[]_.,+=^@!$%";

    for(int i = 0; i < "zxcvbnmasdfghjklqwertyuiopячсмитьбюэждлорпавыфйцукенгшщзхъё1234567890 -(){}[]_.,+=^@!$%".length(); ++i) {
      allowSymbolSet.add("zxcvbnmasdfghjklqwertyuiopячсмитьбюэждлорпавыфйцукенгшщзхъё1234567890 -(){}[]_.,+=^@!$%".charAt(i));
    }

    validSymbolSet = new HashSet(allowSymbolSet);
    validSymbolSet.add('#');
  }

  public static class StopProcessingException extends Exception {
    public StopProcessingException() {
    }
  }
}
