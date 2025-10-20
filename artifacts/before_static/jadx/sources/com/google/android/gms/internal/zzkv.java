package com.google.android.gms.internal;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
abstract class zzkv<K, V> {
    zzkv<K, V>.zzb zzabu;
    zzkv<K, V>.zzc zzabv;
    zzkv<K, V>.zze zzabw;

    final class zza<T> implements Iterator<T> {
        boolean mCanRemove = false;
        int mIndex;
        final int mOffset;
        int mSize;

        zza(int i) {
            this.mOffset = i;
            this.mSize = zzkv.this.colGetSize();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mIndex < this.mSize;
        }

        @Override // java.util.Iterator
        public T next() {
            T t = (T) zzkv.this.colGetEntry(this.mIndex, this.mOffset);
            this.mIndex++;
            this.mCanRemove = true;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.mCanRemove) {
                throw new IllegalStateException();
            }
            this.mIndex--;
            this.mSize--;
            this.mCanRemove = false;
            zzkv.this.colRemoveAt(this.mIndex);
        }
    }

    final class zzb implements Set<Map.Entry<K, V>> {
        zzb() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(Map.Entry<K, V> object) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int iColGetSize = zzkv.this.colGetSize();
            for (Map.Entry<K, V> entry : collection) {
                zzkv.this.colPut(entry.getKey(), entry.getValue());
            }
            return iColGetSize != zzkv.this.colGetSize();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            zzkv.this.colClear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) o;
            int iColIndexOfKey = zzkv.this.colIndexOfKey(entry.getKey());
            if (iColIndexOfKey >= 0) {
                return zzkt.equal(zzkv.this.colGetEntry(iColIndexOfKey, 1), entry.getValue());
            }
            return false;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object object) {
            return zzkv.equalsSetHelper(this, object);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iColGetSize = zzkv.this.colGetSize() - 1;
            int iHashCode = 0;
            while (iColGetSize >= 0) {
                Object objColGetEntry = zzkv.this.colGetEntry(iColGetSize, 0);
                Object objColGetEntry2 = zzkv.this.colGetEntry(iColGetSize, 1);
                iColGetSize--;
                iHashCode += (objColGetEntry2 == null ? 0 : objColGetEntry2.hashCode()) ^ (objColGetEntry == null ? 0 : objColGetEntry.hashCode());
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return zzkv.this.colGetSize() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new zzd();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return zzkv.this.colGetSize();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] array) {
            throw new UnsupportedOperationException();
        }
    }

    final class zzc implements Set<K> {
        zzc() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K object) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            zzkv.this.colClear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object object) {
            return zzkv.this.colIndexOfKey(object) >= 0;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return zzkv.containsAllHelper(zzkv.this.colGetMap(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object object) {
            return zzkv.equalsSetHelper(this, object);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int iColGetSize = zzkv.this.colGetSize() - 1; iColGetSize >= 0; iColGetSize--) {
                Object objColGetEntry = zzkv.this.colGetEntry(iColGetSize, 0);
                iHashCode += objColGetEntry == null ? 0 : objColGetEntry.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return zzkv.this.colGetSize() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new zza(0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object object) {
            int iColIndexOfKey = zzkv.this.colIndexOfKey(object);
            if (iColIndexOfKey < 0) {
                return false;
            }
            zzkv.this.colRemoveAt(iColIndexOfKey);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return zzkv.removeAllHelper(zzkv.this.colGetMap(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return zzkv.retainAllHelper(zzkv.this.colGetMap(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return zzkv.this.colGetSize();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            return zzkv.this.toArrayHelper(0);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) zzkv.this.toArrayHelper(tArr, 0);
        }
    }

    final class zzd implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        int mEnd;
        boolean mEntryValid = false;
        int mIndex = -1;

        zzd() {
            this.mEnd = zzkv.this.colGetSize() - 1;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object o) {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) o;
            return zzkt.equal(entry.getKey(), zzkv.this.colGetEntry(this.mIndex, 0)) && zzkt.equal(entry.getValue(), zzkv.this.colGetEntry(this.mIndex, 1));
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.mEntryValid) {
                return (K) zzkv.this.colGetEntry(this.mIndex, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.mEntryValid) {
                return (V) zzkv.this.colGetEntry(this.mIndex, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mIndex < this.mEnd;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            Object objColGetEntry = zzkv.this.colGetEntry(this.mIndex, 0);
            Object objColGetEntry2 = zzkv.this.colGetEntry(this.mIndex, 1);
            return (objColGetEntry2 != null ? objColGetEntry2.hashCode() : 0) ^ (objColGetEntry == null ? 0 : objColGetEntry.hashCode());
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            this.mIndex++;
            this.mEntryValid = true;
            return this;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.mEntryValid) {
                throw new IllegalStateException();
            }
            zzkv.this.colRemoveAt(this.mIndex);
            this.mIndex--;
            this.mEnd--;
            this.mEntryValid = false;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (this.mEntryValid) {
                return (V) zzkv.this.colSetValue(this.mIndex, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }
    }

    final class zze implements Collection<V> {
        zze() {
        }

        @Override // java.util.Collection
        public boolean add(V object) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            zzkv.this.colClear();
        }

        @Override // java.util.Collection
        public boolean contains(Object object) {
            return zzkv.this.colIndexOfValue(object) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return zzkv.this.colGetSize() == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new zza(1);
        }

        @Override // java.util.Collection
        public boolean remove(Object object) {
            int iColIndexOfValue = zzkv.this.colIndexOfValue(object);
            if (iColIndexOfValue < 0) {
                return false;
            }
            zzkv.this.colRemoveAt(iColIndexOfValue);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int i = 0;
            int iColGetSize = zzkv.this.colGetSize();
            boolean z = false;
            while (i < iColGetSize) {
                if (collection.contains(zzkv.this.colGetEntry(i, 1))) {
                    zzkv.this.colRemoveAt(i);
                    i--;
                    iColGetSize--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int i = 0;
            int iColGetSize = zzkv.this.colGetSize();
            boolean z = false;
            while (i < iColGetSize) {
                if (!collection.contains(zzkv.this.colGetEntry(i, 1))) {
                    zzkv.this.colRemoveAt(i);
                    i--;
                    iColGetSize--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return zzkv.this.colGetSize();
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return zzkv.this.toArrayHelper(1);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) zzkv.this.toArrayHelper(tArr, 1);
        }
    }

    zzkv() {
    }

    public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!map.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static <T> boolean equalsSetHelper(java.util.Set<T> r4, java.lang.Object r5) {
        /*
            r0 = 1
            r1 = 0
            if (r4 != r5) goto L6
            r1 = r0
        L5:
            return r1
        L6:
            boolean r2 = r5 instanceof java.util.Set
            if (r2 == 0) goto L5
            java.util.Set r5 = (java.util.Set) r5
            int r2 = r4.size()     // Catch: java.lang.ClassCastException -> L20 java.lang.NullPointerException -> L22
            int r3 = r5.size()     // Catch: java.lang.ClassCastException -> L20 java.lang.NullPointerException -> L22
            if (r2 != r3) goto L1e
            boolean r2 = r4.containsAll(r5)     // Catch: java.lang.ClassCastException -> L20 java.lang.NullPointerException -> L22
            if (r2 == 0) goto L1e
        L1c:
            r1 = r0
            goto L5
        L1e:
            r0 = r1
            goto L1c
        L20:
            r0 = move-exception
            goto L5
        L22:
            r0 = move-exception
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzkv.equalsSetHelper(java.util.Set, java.lang.Object):boolean");
    }

    public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            map.remove(it.next());
        }
        return size != map.size();
    }

    public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    protected abstract void colClear();

    protected abstract Object colGetEntry(int i, int i2);

    protected abstract Map<K, V> colGetMap();

    protected abstract int colGetSize();

    protected abstract int colIndexOfKey(Object obj);

    protected abstract int colIndexOfValue(Object obj);

    protected abstract void colPut(K k, V v);

    protected abstract void colRemoveAt(int i);

    protected abstract V colSetValue(int i, V v);

    public Set<Map.Entry<K, V>> getEntrySet() {
        if (this.zzabu == null) {
            this.zzabu = new zzb();
        }
        return this.zzabu;
    }

    public Set<K> getKeySet() {
        if (this.zzabv == null) {
            this.zzabv = new zzc();
        }
        return this.zzabv;
    }

    public Collection<V> getValues() {
        if (this.zzabw == null) {
            this.zzabw = new zze();
        }
        return this.zzabw;
    }

    public Object[] toArrayHelper(int offset) {
        int iColGetSize = colGetSize();
        Object[] objArr = new Object[iColGetSize];
        for (int i = 0; i < iColGetSize; i++) {
            objArr[i] = colGetEntry(i, offset);
        }
        return objArr;
    }

    public <T> T[] toArrayHelper(T[] tArr, int i) {
        int iColGetSize = colGetSize();
        if (tArr.length < iColGetSize) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), iColGetSize));
        }
        for (int i2 = 0; i2 < iColGetSize; i2++) {
            tArr[i2] = colGetEntry(i2, i);
        }
        if (tArr.length > iColGetSize) {
            tArr[iColGetSize] = null;
        }
        return tArr;
    }
}
