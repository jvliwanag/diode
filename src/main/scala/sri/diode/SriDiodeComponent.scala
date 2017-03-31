package sri.diode

import diode.{ActionType, Circuit, ModelR, ModelRO}
import sri.core.{Component, CreateElement, JSProps, JSState, ReactElement}

import scala.language.existentials
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSName, ScalaJSDefined}

@ScalaJSDefined
class SriDiodeComponent[M <: AnyRef, S >: Null <: AnyRef]
    extends Component[SriDiodeComponent.Props[M, S], S] {
  import SriDiodeComponent._
//  implicit object aType extends ActionType[Any]

  override def componentWillMount(): Unit = {
    initialState(props.reader())
    unsubscribe = Some(
      props.circuit
        .subscribe(props.reader.asInstanceOf[ModelR[M, S]])(changeHandler))
  }

  def render() =
    props.compB(
      ModelProxy(props.reader,
                 (action: AnyRef) => props.circuit.dispatch(action)))

  override def componentDidMount(): Unit = {
    isMounted = true
  }

  override def shouldComponentUpdate(nextJSProps: JSProps {
    type ScalaProps = SriDiodeComponent.Props[M, S]
  }, nextJSState: JSState { type ScalaState = S }): Boolean = {
    state ne nextJSState.scalaState
  }

  override def componentWillUnmount(): Unit = {
    isMounted = false
    unsubscribe.foreach(f => f())
    unsubscribe = None
  }

  private var unsubscribe = Option.empty[() => Unit]

  private var isMounted: Boolean = false

  private def changeHandler(cursor: ModelRO[S]): Unit = {
    // modify state if we are mounted and state has actually changed
    if (isMounted && cursor =!= state)
      setState((state: S) => cursor.value)
  }
}

object SriDiodeComponent {

  implicit object aType extends ActionType[Any]

  case class Props[M <: AnyRef, S >: Null <: AnyRef](
      reader: ModelR[_, S],
      circuit: Circuit[M],
      compB: ModelProxy[S] => ReactElement)

  def apply[M <: AnyRef, S >: Null <: AnyRef](
      reader: ModelR[_, S],
      circuit: Circuit[M],
      compB: ModelProxy[S] => ReactElement) =
    CreateElement[SriDiodeComponent[M, S]](Props(reader, circuit, compB))

}
