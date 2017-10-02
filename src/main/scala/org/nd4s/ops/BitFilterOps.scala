package org.nd4s.ops

import org.nd4j.linalg.api.complex.IComplexNumber
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.api.ops.{Op, BaseScalarOp}
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.autodiff.functions.DifferentialFunction
import org.nd4j.autodiff.ArrayField
import java.util.{List => JList}
import org.nd4s.Implicits._

object BitFilterOps {
  def apply(x:INDArray,f:Double=>Boolean, g:IComplexNumber => Boolean):BitFilterOps = new BitFilterOps(x,x.length(),f,g)
}

class BitFilterOps(_x:INDArray,len:Int,f:Double => Boolean, g:IComplexNumber => Boolean) extends BaseScalarOp(_x,null:INDArray,_x,len,0) with LeftAssociativeBinaryOp {
  def this(){
    this(0.toScalar,0,null,null)
  }

  x = _x
  override def opNum(): Int = -1

  override def name(): String = "bitfilter_scalar"

  override def opForDimension(index: Int, dimension: Int): Op = BitFilterOps(x.tensorAlongDimension(index,dimension),f,g)

  override def opForDimension(index: Int, dimension: Int*): Op = BitFilterOps(x.tensorAlongDimension(index,dimension:_*),f,g)

  override def op(origin: Double): Double = if(f(origin)) 1 else 0

  override def op(origin: Float): Float = if(f(origin)) 1 else 0

  override def op(origin: IComplexNumber): IComplexNumber = if(g(origin)) Nd4j.createComplexNumber(1,0) else Nd4j.createComplexNumber(0, 0)

  def doGetValue(): ArrayField = null

  def doDiff(l: JList[DifferentialFunction]): JList[DifferentialFunction] = null

}
