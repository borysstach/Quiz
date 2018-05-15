package pl.borys.quiz.common.viewModel

class Response<out T>(val status: Status, val data: T?, val error: Throwable?) {

    fun map(onLoading: () -> Unit, onSuccess: (T?) -> Unit, onError: (Throwable?) -> Unit, onFinish: ()-> Unit) {
        when (status) {
            Status.LOADING -> onLoading()
            Status.SUCCESS -> {
                onSuccess(data)
                onFinish()
            }
            Status.ERROR -> {
                onError(error)
                onFinish()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Response<*> && other.status == this.status && other.data == this.data && other.error == this.error
    }

    fun isLoading() = status == Status.LOADING
    fun isError() = status == Status.ERROR

    companion object {
        fun loading() = Response(Status.LOADING, null, null)
        fun <T> success(data: T) = Response(Status.SUCCESS, data, null)
        fun error(throwable: Throwable) = Response(Status.ERROR, null, throwable)
    }
}