package com.eagalts.exta.BugFixes.AlgebraOfLogic

/*
==================================
      exta: extension file
==================================
*/

/*

Extension: Algebra of logic
- Version: 1.0.0
- Development date: 03/10/2023
- Developer: eagalts
- Library: exta-main (standard extension)

Functional:
- XOR function
- XNOR function

*/

class AlgebraOfLogic {
    private fun checkIfXORUses(inputString : String): Boolean {
        return (inputString.contains("xor("))
    }

    private fun useXOR(inputString: String) : String {
        var finalValue = inputString
        finalValue = if (finalValue.contains("xor(0"))
            finalValue.replace("xor(0,0)", "0").replace("xor(0, 0)", "0").replace("xor(0,1)", "1").replace("xor(0, 1)", "1")
        else
            finalValue.replace("xor(1,1)", "0").replace("xor(1, 1)", "0").replace("xor(1,0)", "1").replace("xor(1, 0)", "1")

        return finalValue
    }

    private fun checkIfXNORUses(inputString : String): Boolean {
        return (inputString.contains("xnor("))
    }

    private fun useXNOR(inputString: String) : String {
        var finalValue = inputString
        finalValue = if (finalValue.contains("xnor(0"))
            finalValue.replace("xnor(0,0)", "1").replace("xnor(0, 0)", "1").replace("xnor(0,1)", "0").replace("xnor(0, 1)", "0")
        else
            finalValue.replace("xnor(1,1)", "1").replace("xnor(1, 1)", "1").replace("xnor(1,0)", "0").replace("xnor(1, 0)", "0")

        return finalValue
    }

    fun useLogic(inputString: String) : String {
        var finalString = inputString
        if (checkIfXORUses(finalString))
            finalString = useXOR(finalString)
        if (checkIfXNORUses(finalString))
            finalString = useXNOR(finalString)
        return finalString
    }
}
