# Processing Linear Regression Visualization

This project demonstrates how **Processing (Java mode)** can be used as a lightweight
tool to **visualize how an algorithm works step by step**, not just its final result.

The example uses **linear regression trained with stochastic gradient descent (SGD)**,
where **one frame equals one learning step**, making the learning process observable
in real time.

---

## Project Structure

The code is intentionally split into two layers:

- **ML layer** – pure Java implementation of linear regression
- **Visualization layer** – Processing sketch responsible only for rendering and interaction

This separation keeps the algorithm testable and the visualization simple.

---

## What you can see

- data points plotted on screen
- a regression line that learns gradually
- clear convergence behavior (the model stops when fitted)
- the effect of learning rate on stability

The goal is understanding, not performance.

---

## Interaction

- **SPACE** – pause / resume training
- **R** – reset the model
- **↑ / ↓** – increase / decrease learning rate
- **Mouse click** – add a new data point

---

## Why Processing?

Processing excels at:
- algorithm visualization
- educational demos
- presentations and technical blog examples
- turning abstract logic into something visible

If you want to *see* how your code behaves over time, Processing is often the simplest tool.

---

## License

MIT
