require(MASS)							#  Подключаем пакет, который содержит функцию nvrnorm()

analyse_cor <- function(x, y) {         
  print(cor.test(x, y, conf.level = 0.05))                 # cor.test() - проверка гипотезы о некоррелированности
  dev.new()                             # открыть новое окно графика
  plot(x, y)							# график исходных данных (в новом окне)
}

dat <- read.table("wine.csv", sep=",")			# загрузка данных из текст. файла
analyse_cor(dat$V2, dat$V11)		# вызов analyse_cor() для 2 и 11 столбцов данных

n <- 10000									
a <- c(-1, 1)							# c() - определить коллекцию (вектор)
r <- cbind(c(1, 0.1), c(0.1, 2))		# cbind() - обьединить вектора в матрицу
dat <- mvrnorm(n, a, r)					# mvnorm() - распределение гаусса с заданными параметрами
analyse_cor(dat[,1], dat[,2])
