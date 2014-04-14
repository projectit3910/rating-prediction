distance.matrix <- function(df) {
  distance <- matrix(rep(NA, nrow(df) ^ 2), nrow = nrow(df))
  for (i in 1:nrow(df)) {
    for (j in 1:nrow(df)) {
      distance[i, j] <-
      sqrt((df[i, 'X'] - df[j, 'X']) ^ 2 + (df[i, 'Y'] - df[j, 'Y']) ^ 2)
    }
  }
  return(distance)
}


k.nearest.neighbors <- function(i, distance, k = 5) {
  return(order(distance[i, ])[2:(k + 1)])
}


knn <- function(df, k = 5)
{
  distance <- distance.matrix(df)
  predictions <- rep(NA, nrow(df))
  for (i in 1:nrow(df))
  {
    indices <- k.nearest.neighbors(i, distance, k = k) predictions[i] <- ifelse(mean(df[indices, 'Label']) > 0.5, 1, 0)
  }
  return(predictions)
}
