package com.example.themoviemvvm.core

abstract class Error(
    override val message: String? = null,
    override val cause: Exception? = null
) : Exception() {

    /**
     * Device has no internet connection
     */
    class NoInternet(
        override val message: String?,
        override val cause: Exception?,
        val showAsDialog: Boolean = true
    ) : Error(cause = cause)

    /**
     * We could not reach a remote data source
     */
    class NoExternalCommunication(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)

    /**
     * There is no authentication
     */
    class Unauthorized(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)

    /**
     * An IO operation has failed
     */
    class BadNetworkResponse(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)

    /**
     * The user has not access to a resource
     */
    class LackOfAccess(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)

    /**
     * The user was not found in Preferences
     */
    class NoUserFound(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)

    /**
     * Has occurred an http exception
     */
    class UnexpectedHttpResponse(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)

    /**
     * Has occurred an http exception
     */
    class FirebaseFetch(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)

    /**
     * We could not determinate the error source,
     * this should not be commonly used,
     * it should be last option when a crash is not a good one
     */
    class Unknown(
        override val message: String?,
        override val cause: Exception?
    ) : Error(cause = cause)
}
