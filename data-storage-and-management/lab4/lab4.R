install.packages("rgl")

require(class)
require(MASS)
require(rgl)

plot_points <- function(train, test, clazz.train,
                        clazz.test) {
  rgl.open()
  plot3d(train[, 1], train[, 2], train[, 3],
         col=clazz.train, type='p', size=5, add=FALSE)
  plot3d(test[, 1], test[, 2], test[, 3],
         col=clazz.test, type='s', size=1, add=TRUE)
}

analyse_knn <- function(dat, clazz) {
  n <- nrow(dat)
  rnd.num <- sample(1 : n)
  train.num <- rnd.num[1 : (n %/% 2)]
  test.num <- rnd.num[(n %/% 2 + 1) : n]
  train <- dat[train.num,]
  test <- dat[test.num,]
  clazz.train <- clazz[train.num]
  clazz.test <- clazz[test.num]
  clazz.knn <- knn(train, test, clazz.train)
  print(sum(clazz.test != clazz.knn) / n)
  plot_points(train, test, clazz.train, clazz.test)
  plot_points(train, test, clazz.train, clazz.knn)
}

dat <- read.table("wine.csv", sep=",")
analyse_knn(cbind(dat$V2, dat$V11, dat$V13), unclass(dat$V1))

n1 <- 100
a1 <- c(-2, 2, -2)
r1 <- cbind(c(2, 1, 1), c(1, 4, 1.4), c(1, 1.4, 2))
n2 <- 50
a2 <- c(4, 8, -8)
r2 <- cbind(c(4, 0.2, 0.3), c(0.2, 2, 0.4), c(0.3, 0.4, 2))

dat <- rbind(mvrnorm(n1, a1, r1), mvrnorm(n2, a2, r2))
analyse_knn(dat, c(rep(1, n1), rep(2, n2)))