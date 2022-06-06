package com.arnoract.piggiplanstation.core


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class Result<out ResultType> {
	object Progress : Result<Nothing>()
	data class Success<ResultType>(val data: ResultType) : Result<ResultType>()
	data class Failure(val exception: Throwable) : Result<Nothing>()

	/**
	 * Returns `true` if this instance represents successful outcome.
	 * In this case [isFailure, isProgress] returns `false`.
	 */
	val isSuccess: Boolean get() = this is Success

	/**
	 * Returns `true` if this instance represents failed outcome.
	 * In this case [isSuccess, isProgress] returns `false`.
	 */
	val isFailure: Boolean get() = this is Failure

	/**
	 * Returns `true` if this instance represents progress.
	 * In this case [isSuccess, isFailure] returns `false`.
	 */
	val isProgress: Boolean get() = this is Progress

	override fun toString(): String {
		return when (this) {
			is Success -> "Success[data=$data]"
			is Failure -> "Failure[exception=${exception.message}]"
			is Progress -> "Progress"
		}
	}
}

fun <T> Result<T>.successOr(fallback: T): T {
	return (this as? Result.Success<T>)?.data ?: fallback
}

fun <T> Result<T>.successOrThrow(): T {
	return when (this) {
		is Result.Success -> this.data
		is Result.Failure -> throw this.exception
		is Result.Progress -> throw throw IllegalStateException()
	}
}

inline fun <T, U> Result<T>.map(onSuccess: (T) -> U): Result<U> {
	return when (this) {
		is Result.Success -> Result.Success(onSuccess(this.data))
		is Result.Failure -> Result.Failure(this.exception)
		is Result.Progress -> Result.Progress
	}
}

inline fun <T, U> Result<T>.fold(
	onFailure: (Throwable) -> T,
	transform: (T) -> U
): U {
	return when (this) {
		is Result.Success -> transform(this.data)
		is Result.Failure -> transform(onFailure(this.exception))
		is Result.Progress -> throw IllegalStateException()
	}
}

inline fun <T> Result<T>.onProgress(action: () -> Unit): Result<T> {
	if (this.isProgress) {
		action()
	}
	return this
}

inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
	if (this.isSuccess) {
		action((this as Result.Success).data)
	}
	return this
}

inline fun <T> Result<T>.onFailure(action: (value: Throwable) -> Unit): Result<T> {
	if (this.isFailure) {
		action((this as Result.Failure).exception)
	}
	return this
}
