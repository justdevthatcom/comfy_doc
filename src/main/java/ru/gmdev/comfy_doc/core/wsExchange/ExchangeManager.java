package ru.gmdev.comfy_doc.core.wsExchange;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.gmdev.comfy_doc.core.wsExchange.Envelope;

import java.nio.charset.Charset;
import java.util.Date;

@Slf4j
@NoArgsConstructor
@Component("EX")
public class ExchangeManager {
  public static final Charset DATABASE_ENCODING = Charset.forName("windows-1251");
  private static volatile long correctionTime;
  private static volatile long correction;
  private static volatile Long timezone;

  public static Date correctionTime() {
    return new Date(correctionTime);
  }

  public Envelope send() {
    log.info("Start send method");


    return new Envelope();
  }
}
