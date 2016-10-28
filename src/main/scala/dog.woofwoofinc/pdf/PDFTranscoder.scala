package dog.woofwoofinc.pdf

import org.apache.batik.apps.rasterizer.DestinationType
import org.apache.batik.transcoder.{ TranscoderOutput, TranscoderInput, Transcoder }
import java.io.{ FileOutputStream, FileInputStream }

object PDFTranscoder {
  private def using[A <: { def close(): Unit }, B](param: A)(f: A => B): B = try { f(param) } finally { param.close() }

  implicit def destinationType2GetTranscoder(destinationType: DestinationType) = new {
    def getTranscoder(): Transcoder = {
      // FFS
      val getTranscoder = (destinationType.getClass().getDeclaredMethods() filter { _.getName == "getTranscoder" }).head
      getTranscoder.setAccessible(true)
      getTranscoder.invoke(destinationType).asInstanceOf[Transcoder]
    }
  }

  def transcode(infile: String, outfile: String) {
    using(new FileInputStream(infile)) { in =>
      using(new FileOutputStream(outfile)) { out =>
        DestinationType.PDF.getTranscoder.transcode(new TranscoderInput(in), new TranscoderOutput(out))
      }
    }
  }
}