package club.youtee.filerename.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xinglong.Li
 * @date 2024-10-18
 */
public class EpisodeFileTest {

    @Test
    public void extractEpCase1() {
        String fileName = "[DBD-Raws][Mushoku Tensei II Isekai Ittara Honki Dasu][04][1080P][BDRip][HEVC-10bit][FLAC].mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(4, ep);
    }

    @Test
    public void extractEpCase2() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - 50 [WebRip 1080p HEVC-10bit AAC SRTx2].mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(50, ep);
    }

    @Test
    public void extractEpCase3() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - 50.mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(50, ep);
    }

    @Test
    public void extractEpCase4() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - 50v2 [WebRip 1080p HEVC-10bit AAC SRTx2].mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(50, ep);
    }

    @Test
    public void extractEpCase5() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - e50V2.aac [WebRip 1080p HEVC-10bit AAC SRTx2].mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(50, ep);
    }

    @Test
    public void extractEpCase6() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - e50V2.mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(50, ep);
    }

    @Test
    public void extractEpCase7() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - e50.aac.mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(50, ep);
    }

    @Test
    public void extractEpCase8() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - 第 50 話 [WebRip 1080p HEVC-10bit AAC SRTx2].mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertEquals(50, ep);
    }

    @Test
    public void extractEpCase9() {
        String fileName = "[LoliHouse] Tensei Shitara Slime Datta Ken 3rd Season - 50.5 [WebRip 1080p HEVC-10bit AAC SRTx2].mkv";
        Integer ep = new EpisodeFile(fileName).getEpisode();
        assertNull(ep);
    }
}
