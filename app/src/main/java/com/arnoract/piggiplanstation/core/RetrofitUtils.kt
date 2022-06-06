package com.arnoract.piggiplan.core

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.arnoract.piggiplanstation.core.Result
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.source
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

/**
 * Synthetic sugaring to create Retrofit Service.
 */
inline fun <reified T> Retrofit.create(): T = create(T::class.java)

/**
 * Converts Retrofit [Response] to [Result]
 */
fun <ResultType> Response<ResultType>.toResult(): Result<ResultType?> {
    return when {
        isSuccessful -> Result.Success(body())
        else -> Result.Failure(WnHttpException(this))
    }
}

/**
 * Converts Retrofit [Response] to [Result] unsafely.
 */
fun <ResultType> Response<ResultType>.unSafeToResult(): Result<ResultType> {
    return when {
        isSuccessful -> Result.Success(body()!!)
        else -> Result.Failure(WnHttpException(this))
    }
}

class WnHttpException(response: Response<*>) : HttpException(response) {
    override val message: String?
        get() = "${super.message}\n${response().toString()}"
}

fun RequestBody?.isFormUrlEncoded() =
    this?.contentType()?.subtype?.equals("x-www-form-urlencoded") ?: false

fun RequestBody?.isJson() = this?.contentType()?.subtype?.equals("json") ?: false

fun Request.isPostRequest() = method.equals("post", ignoreCase = true)

fun Request.isPutRequest() = method.equals("put", ignoreCase = true)

fun Request.isDeleteRequest() = method.equals("delete", ignoreCase = true)

fun Request.isGetRequest() = method.equals("get", ignoreCase = true)

fun RequestBody?.bodyToString(): String {
    try {
        val copy = this
        val buffer = Buffer()
        if (copy != null) {
            copy.writeTo(buffer)
        } else {
            return ""
        }
        return buffer.readUtf8()
    } catch (e: IOException) {
        return ""
    }
}

fun Uri.createMultiPart(
    context: Context,
    imageName: String
): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        "photo",
        imageName,
        ContentUriRequestBody(context.contentResolver, this)
    )
}

class ContentUriRequestBody(
    private val contentResolver: ContentResolver,
    private val contentUri: Uri
) : RequestBody() {

    override fun contentType(): MediaType? {
        val contentType = contentResolver.getType(contentUri)
        return contentType?.toMediaTypeOrNull()
    }

    override fun writeTo(sink: BufferedSink) {
        val inputStream = contentResolver.openInputStream(contentUri)
            ?: throw IOException("Couldn't open content URI for reading")

        inputStream.source().use { source ->
            sink.writeAll(source)
        }
    }
}
