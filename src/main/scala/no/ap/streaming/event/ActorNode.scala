package no.ap.streaming.event

import spray.json._


abstract class ActorObject {
  def id: String
  def environmentId: String
  def sessionId: String
}

case class ActorNode(typ: String, id: String, environmentId: String, sessionId: String) extends ActorObject

case class ActorReadNode(typ: String, userAgent: String, screenSize: String, viewPort: String, acceptLanguage: String,
                         id: String, environmentId: String, sessionId: String) extends ActorObject


object ActorNodeProtocol extends DefaultJsonProtocol {

  implicit object ActorFormatter extends RootJsonFormat[ActorObject] {
    override def write(actor: ActorObject): JsValue = {
      JsArray(JsString(actor.id), JsString(actor.environmentId), JsString(actor.sessionId))
    }

    override def read(json: JsValue): ActorObject = {
      json.asJsObject.getFields("@type", "spt:userAgent", "spt:screenSize", "spt:viewportSize", "spt:acceptLanguage",
        "@id", "spt:environmentId", "spt:sessionId") match {
        case Seq(JsString(tp), JsString(userAgent), JsString(screenSize), JsString(viewportSize), JsString(acceptLanguage), JsString(id), JsString(environmentId), JsString(sessionId))
        => new ActorReadNode(tp, userAgent, screenSize, viewportSize, acceptLanguage, id, environmentId, sessionId)
        case Seq(JsString(tp), JsString(id), JsString(environmentId), JsString(sessionId))
        => new ActorNode(tp, id, environmentId, sessionId)
        case _ => throw new DeserializationException("ActorNode expected" + json.compactPrint)
      }
    }
  }

}