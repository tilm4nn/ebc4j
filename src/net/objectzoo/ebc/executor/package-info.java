/**
 * This package contains classes that help when there is need to synchronously execute an EBC flow.
 * Usually you should consider twice when using them since this contradicts the idea behind flow
 * design. But there may be cases when this makes sense e.g. when you are interfacing with a non
 * flow design implementation.
 */
package net.objectzoo.ebc.executor;