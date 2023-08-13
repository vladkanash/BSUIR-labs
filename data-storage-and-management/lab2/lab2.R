analyse_regression <- function(x, y) {
  model <- lm(y ~ x)
  print(summary(model))
  dev.new()
  plot(x, y)
  abline(model)
}

dat <- read.table("wine.csv", sep=",")
analyse_regression(dat$V2, dat$V11)
n <- 10000
a <- 0.5
b <- 1
s2 <- 1
x <- seq(0.0, 1.0, length=n)
y <- a * x + b + rnorm(n, 0, s2)
analyse_regression(x, y)