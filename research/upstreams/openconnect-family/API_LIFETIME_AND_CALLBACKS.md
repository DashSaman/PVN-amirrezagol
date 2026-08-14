# OpenConnect — Public API Lifetime / Callback / Binding Contract Research

Review date: 2026-08-14

Canonical evidence: current `openconnect.h`, `library.c`, protocol source and current upstream issues/MRs.

Status: `IN-RESEARCH`. This file replaces the previously blocked attempt at one large adapter-map document with a smaller focused contract.

## 1. Session context ownership

`openconnect_vpninfo_new()` constructs the library session context and accepts frontend callbacks including certificate validation, new-config handling, authentication-form processing and progress/log reporting.

PVNetwork binding rule:

- one Enterprise Adapter session owns one `openconnect_info` context;
- raw context pointers never enter UI/business models;
- cleanup/free is owned by the adapter session object;
- cancellation/teardown is serialized through that owner.

## 2. Authentication callbacks are a frontend contract, not a fixed dialog

OpenConnect protocol code constructs `oc_auth_form` structures and calls the registered `process_auth_form` callback. The same abstraction is used for ordinary user/password prompts, group/realm choices, token/challenge flows and even crypto-provider PIN prompts.

Evidence from current source includes:

- protocol code creating text/password/select forms;
- OpenSSL UI integration converting provider/engine prompts into the same auth-form callback model;
- Pulse code using the callback for realm selection, primary/secondary credentials, password changes and token/challenge responses.

PVNetwork requirement:

- implement one generic Auth Challenge Model that can represent these forms without vendor-specific hardcoding;
- copy required values into PVNetwork-owned UI models before crossing threads/process boundaries;
- never expose C linked-list pointers directly to UI state.

## 3. Callback result values affect protocol state

`process_auth_form` return values include state such as cancellation and `OC_FORM_RESULT_NEWGROUP`. The library may re-enter/retry processing when a group/realm selection changes.

Current issue #812 is a useful frontend-integration lesson: a real library user reported unexpected auth-group behavior around a worker thread waiting for UI input and returning `OC_FORM_RESULT_NEWGROUP`.

PVNetwork tests:

- group/realm change while form is active;
- callback returns `NEWGROUP` exactly once and library/frontend converge on the same selected value;
- cancel while waiting for UI input;
- repeated group changes do not deadlock;
- reconnect does not reuse stale form state.

## 4. Sensitive form values must not outlive their purpose

Current library source clears/nukes text/password option values on cancelled/error paths in authentication processing.

PVNetwork should preserve the same principle above the C boundary:

- password/token values exist only in protected transient models;
- wipe/drop transient secret copies after callback completion when no longer needed;
- do not retain auth-form password pointers in observable/shared UI state;
- never serialize raw form secrets into logs or crash reports.

## 5. Library-owned runtime structures can be invalidated

Current public header documentation explicitly warns that some returned runtime structures are library-owned and can be freed/replaced due to rekey or reconnect after the main loop starts.

PVNetwork rule:

- treat library-returned pointer data as borrowed snapshots;
- copy the fields needed for UI/statistics into immutable PVNetwork-owned models before returning from the adapter boundary;
- never cache library-owned pointers in long-lived state;
- refresh statistics/runtime information after reconnect/rekey events.

## 6. Some returned buffers require library-specific free functions

Current public header documentation states that certificate detail/DER buffers returned by relevant APIs must be freed through `openconnect_free_cert_info()`, with an explicit warning that this matters especially on Windows.

PVNetwork FFI rule:

- every C API wrapper records allocator/free ownership;
- use library-provided free functions for library-allocated buffers;
- never assume the application runtime allocator can free library memory across Windows DLL/runtime boundaries;
- add leak/double-free tests around certificate/trust UI.

## 7. Authentication and tunnel connection may be split across security contexts

OpenConnect's public interface/documentation supports an authentication-only phase whose result is later consumed by the connection phase.

PVNetwork implication:

- the auth frontend may run in a user/UI context while privileged tunnel/network work runs elsewhere;
- the handoff object must contain only the minimum short-lived connection material;
- browser/SSO/certificate access and privileged network control do not need to live in one process/thread;
- secret handoff needs explicit lifetime and cancellation semantics.

## 8. Webview / external-browser callbacks are API-versioned features

Real downstream integration code in upstream issue reports checks OpenConnect API version before installing webview/external-browser callbacks. This demonstrates that SSO frontend capability is version-dependent at the public API boundary.

PVNetwork rule:

- compile-time API checks are not enough; record the minimum supported library/API version in the adapter;
- capability registry should report browser/webview availability;
- never advertise SSO to a server if the active frontend/platform cannot complete the corresponding callback flow.

## 9. Threading policy for PVNetwork — conservative until proven

This research has **not** established a blanket guarantee that arbitrary OpenConnect API calls are safe concurrently from any thread.

Therefore the future binding should default to:

- one serialized session executor/event loop per active `openconnect_info`;
- callbacks marshalled into the product/UI thread through copied data models;
- responses marshalled back to the session executor;
- cancellation delivered through the documented cancellation/control mechanism rather than racing direct lifecycle calls;
- no simultaneous start/stop/reconnect mutation from multiple UI surfaces.

If later upstream documentation/source proves specific calls thread-safe, narrow exceptions can be documented explicitly.

## 10. UI wait / callback deadlock prevention

A GUI callback can need to wait for user input while the core waits for the callback result. This is a natural deadlock/race boundary.

PVNetwork requirements:

- never block the main UI event loop waiting for the core while the core is waiting for UI;
- callback bridge uses async request/response or a dedicated worker/session executor;
- cancellation wakes both sides;
- app shutdown must release any blocked auth request;
- browser/SSO return and ordinary form input use the same session correlation ID.

## 11. Error mapping boundary

Low-level errno/library return values should not leak directly into product UX.

The adapter should retain:

- raw upstream code/message for diagnostics;
- stable PVNetwork error category;
- protocol/vendor phase (`dns`, `tls`, `certificate`, `auth-form`, `sso`, `tunnel`, `reconnect`, `cleanup`);
- recoverable vs terminal classification;
- whether user action is required.

## 12. Binding regression tests

Before shipping any binding:

1. create/free context repeatedly under leak detection;
2. cancel during DNS/TLS/auth/browser/tunnel phases;
3. auth form group change/re-entry;
4. password/token value cleanup;
5. certificate buffer ownership/freeing;
6. callback after UI activity recreation/window closure;
7. reconnect/rekey invalidates and refreshes borrowed runtime data;
8. simultaneous tray/main-window commands serialize correctly;
9. library upgrade keeps required callback/API semantics;
10. process/helper shutdown cannot leave UI waiting forever.

## Remaining gaps

- document exact cancel/control APIs and wake-up behavior from current header/source;
- enumerate every public callback and its ownership semantics;
- map public error/return codes to the proposed PVNetwork taxonomy;
- verify exact thread-safety statements from upstream maintainers/docs;
- prototype FFI bindings on selected implementation language after architecture selection.