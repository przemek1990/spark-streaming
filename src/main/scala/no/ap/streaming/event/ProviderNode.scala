package no.ap.streaming.event

import spray.json._

case class ProviderNode(providerType: String, id: String, url: String)


object ProviderNodeProtocol extends DefaultJsonProtocol {

  implicit object ProviderFormatter extends RootJsonFormat[ProviderNode] {
    override def write(provider: ProviderNode): JsValue = {
      JsArray(JsString(provider.providerType), JsString(provider.id), JsString(provider.url))
    }

    override def read(json: JsValue): ProviderNode = {
      json.asJsObject.getFields("@type", "@id", "url") match {
        case Seq(JsString(tp), JsString(id), JsString(url)) =>
          new ProviderNode(tp, id, url)
        case _ => throw new DeserializationException("ProviderNode expected")
      }
    }
  }

}