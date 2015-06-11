package no.ap.streaming.event

import no.ap.streaming.event.ActorNodeProtocol._
import no.ap.streaming.event.ProviderNodeProtocol._
import spray.json._

case class RootEvent(id: String, published: String, sptPageViewId: String, actor: ActorObject, provider: ProviderNode, eventType: String)

object RootProtocol extends DefaultJsonProtocol {

  implicit object RootEventJsonFormat extends RootJsonFormat[RootEvent] {
    override def write(rootEvent: RootEvent): JsValue = {
      JsArray(JsString(rootEvent.id), JsString(rootEvent.published), JsString(rootEvent.sptPageViewId),
        rootEvent.actor.toJson, rootEvent.provider.toJson, JsString(rootEvent.eventType))
    }

    override def read(json: JsValue): RootEvent = {
      json.asJsObject.getFields("@id", "published", "spt:pageViewId", "actor", "provider", "@type") match {
        case Seq(JsString(id), JsString(published), JsString(sptPageViewId), actor, provider, JsString(tp)) =>
          new RootEvent(id, published, sptPageViewId, actor.convertTo[ActorObject], provider.convertTo[ProviderNode], tp)
        case _ => throw new DeserializationException("RootEvent expected")
      }
    }
  }

}

