package no.ap.streaming.event

import no.ap.streaming.event.ActorNodeProtocol._
import no.ap.streaming.event.ProviderNodeProtocol._
import spray.json._

case class ActivityStreamEvent(id: String, published: String, sptPageViewId: String, actor: ActorObject, provider: ProviderNode, eventType: String)

object ActivityStreamProtocol extends DefaultJsonProtocol {

  implicit object RootEventJsonFormat extends RootJsonFormat[ActivityStreamEvent] {
    override def write(rootEvent: ActivityStreamEvent): JsValue = {
      JsArray(JsString(rootEvent.id), JsString(rootEvent.published), JsString(rootEvent.sptPageViewId),
        rootEvent.actor.toJson, rootEvent.provider.toJson, JsString(rootEvent.eventType))
    }

    override def read(json: JsValue): ActivityStreamEvent = {
      json.asJsObject.getFields("@id", "published", "spt:pageViewId", "actor", "provider", "@type") match {
        case Seq(JsString(id), JsString(published), JsString(sptPageViewId), actor, provider, JsString(tp)) =>
          new ActivityStreamEvent(id, published, sptPageViewId, actor.convertTo[ActorObject], provider.convertTo[ProviderNode], tp)
        case _ => throw new DeserializationException("RootEvent expected")
      }
    }
  }

}

