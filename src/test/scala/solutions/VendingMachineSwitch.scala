// See LICENSE.txt for license details.
package solutions

import Chisel.iotesters.{ Backend => TesterBackend, _ }

class VendingMachineSwitchTests(c: VendingMachineSwitch, b: Option[TesterBackend] = None) extends PeekPokeTester(c, _backend=b) {
  var money = 0
  var isValid = false
  for (t <- 0 until 20) {
    val coin     = rnd.nextInt(3)*5
    val isNickel = coin == 5
    val isDime   = coin == 10

    // Advance circuit
    poke(c.io.nickel, if (isNickel) 1 else 0)
    poke(c.io.dime,   if (isDime) 1 else 0)
    step(1)

    // Advance model
    money = if (isValid) 0 else (money + coin)
    isValid = money >= 20

    // Compare
    expect(c.io.valid, if (isValid) 1 else 0)
  }
}
