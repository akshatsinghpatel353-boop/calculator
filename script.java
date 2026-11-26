
let currentInput = "0";
let firstOperand = null;
let operator = null;
let shouldReset = false;

const result = document.getElementById("result");
const previous = document.getElementById("previous");

// Update display
function updateDisplay() {
    result.value = currentInput;
}

// Enter numbers
function appendNumber(number) {

    if (shouldReset) {
        currentInput = "0";
        shouldReset = false;
    }

    if (number === "." && currentInput.includes(".")) {
        return;
    }

    if (currentInput === "0" && number !== ".") {
        currentInput = number;
    } else {
        currentInput += number;
    }

    updateDisplay();
}

// Select operator
function chooseOperator(op) {

    if (operator !== null && !shouldReset) {
        calculate();
    }

    firstOperand = parseFloat(currentInput);
    operator = op;
    shouldReset = true;

    previous.textContent = firstOperand + " " + op;
}

// Calculate result
function calculate() {

    if (operator === null || shouldReset) {
        return;
    }

    let secondOperand = parseFloat(currentInput);
    let answer;

    switch (operator) {

        case "+":
            answer = firstOperand + secondOperand;
            break;

        case "-":
            answer = firstOperand - secondOperand;
            break;

        case "*":
            answer = firstOperand * secondOperand;
            break;

        case "/":
            if (secondOperand === 0) {
                currentInput = "Cannot divide by zero";
                operator = null;
                firstOperand = null;
                shouldReset = true;
                previous.textContent = "";
                updateDisplay();
                return;
            }

            answer = firstOperand / secondOperand;
            break;
    }

    answer = Number(answer.toPrecision(12));

    previous.textContent =
        firstOperand + " " + operator + " " + secondOperand + " =";

    currentInput = String(answer);

    operator = null;
    firstOperand = null;
    shouldReset = true;

    updateDisplay();
}

// Clear calculator
function clearDisplay() {

    currentInput = "0";
    firstOperand = null;
    operator = null;
    shouldReset = false;

    previous.textContent = "";

    updateDisplay();
}

// Delete last digit
function deleteNumber() {

    if (shouldReset || currentInput === "Cannot divide by zero") {
        currentInput = "0";
        shouldReset = false;
    } else {
        currentInput = currentInput.slice(0, -1);

        if (currentInput === "" || currentInput === "-") {
            currentInput = "0";
        }
    }

    updateDisplay();
}

// Keyboard support
document.addEventListener("keydown", function(event) {

    if (event.key >= "0" && event.key <= "9") {
        appendNumber(event.key);
    }

    else if (event.key === ".") {
        appendNumber(".");
    }

    else if (["+", "-", "*", "/"].includes(event.key)) {
        chooseOperator(event.key);
    }

    else if (event.key === "Enter" || event.key === "=") {
        calculate();
    }

    else if (event.key === "Backspace") {
        deleteNumber();
    }

    else if (event.key === "Escape") {
        clearDisplay();
    }

});
